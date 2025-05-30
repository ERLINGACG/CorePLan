#include "webcore/WebCore.h"
#include "webcore/dnn/DeepNeuralNetwork.h"
using namespace deepneuralnetwork;

DeepNeuralNetworkYolo::DeepNeuralNetworkYolo(webcoreimage::CoreImage* coreImage, const char* path) {
    this->coreImage = coreImage;
    this->path = path;
    // this->net = readNetFromONNX(this->path);
}
int DeepNeuralNetworkYolo::ReadNetFromONNX(){
   try{

        this->net = readNetFromONNX(this->path);
        std::cout << "加载模型成功" << std::endl;
        return 0;

   }catch(...){
        std::cout << "加载模型失败" << std::endl;
        return -1;
   }
}
Mat DeepNeuralNetworkYolo::ForMat(const Mat& source){
          //   int col=source.cols;
          //   int row=source.rows;
          //   cout<<"row:"<<row<<endl;
          //   cout<<"col:"<<col<<endl;
          //   int _max = (max)(col,row); //防止Windows系统宏max报错
          //   Mat result = Mat::zeros(_max, _max,CV_8UC3);
          //   source.copyTo(result(Rect(0, 0, col, row)));
	     // return result;
   // 自动计算缩放比例
    float scale = min(640.0f/source.cols, 640.0f/source.rows);
    Size new_size(source.cols * scale, source.rows * scale);
    
    Mat resized;
    resize(source, resized, new_size);  // 等比例缩放
    
    // 计算填充尺寸
    int delta_w = 640 - new_size.width;
    int delta_h = 640 - new_size.height;
    
    // 中心填充
    copyMakeBorder(resized, resized, 
                  delta_h/2, delta_h - delta_h/2,
                  delta_w/2, delta_w - delta_w/2,
                  BORDER_CONSTANT, Scalar(114,114,114));
    return resized;
}
std::unique_ptr<ImageStruct> DeepNeuralNetworkYolo::DetectImage(
                unsigned char*  image,
                int             size,
                const char*     imagecode,
                int             imageQos
){
     std::vector<unsigned char> buf(image, image + size);
      // 修改为使用彩色模式解码（YOLO需要RGB输入）
     cv::Mat input = cv::imdecode(buf,cv::IMREAD_COLOR); // 原为IMREAD_GRAYSCALE
     std::cout<<"解码完成"<<std::endl;

     Mat frame=this->ForMat(input);

     Mat blob=blobFromImage(frame, 1/255.0, Size(640,640), Scalar(0, 0, 0), true, false);
     std::cout<<"blob完成"<<std::endl;

     this->net.setInput(blob);
     vector<string> outputs_name= this->net.getUnconnectedOutLayersNames();
     vector<Mat> output_mat;
     this->net.forward(output_mat,outputs_name);
     std::cout<<"forward完成"<<std::endl;


      float* data = (float*)output_mat[0].data;
     float x_factor = 640.0f / frame.cols;  // 使用实际输入尺寸
     float y_factor = 640.0f / frame.rows;

      //yolov5s模型的输出大小为[1,25200.85]
     const int dimensions = 85;
     const int rows = 25200;

     //分类类别索引
	vector<int> class_ids;

     //置信度
	vector<float> confidences;
     //边框坐标信息
	vector<cv::Rect> boxes;

     for(int i = 0; i < rows; i++){
                    
		          float confidence = data[4]; //置信度
                    if(confidence > 0.45){    //过滤置信度低的框
                        float* classes_scores = data + 5;    //分类分数
                        cv::Mat scores(1, this->classNames.size(), CV_32FC1, classes_scores); //转换为Mat
                        cv::Point class_id;     //类别索引
                        double max_class_score; //最大分数
                        
			            minMaxLoc(scores, 0, &max_class_score, 0, &class_id);   //获取最大分数的类别索引
                        if(max_class_score > 0.21){   //过滤分数低的框
                            confidences.push_back(confidence);//置信度
				            class_ids.push_back(class_id.x);  //类别索引

                            float x = data[0];
                            float y = data[1];
                            float w = data[2];
                            float h = data[3];
                            

                            int left = int((x - 0.5 * w) * x_factor); //左上角x坐标
				            int top = int((y - 0.5 * h) * y_factor); //左上角y坐标
				            int width = int(w * x_factor);           //宽度
				            int height = int(h * y_factor);          //高度
                            cout<<"x:"<<x<<" left:"<<left<<endl;   
                            cout<<"y:"<<y<<" top:"<<top<<endl;
                            cout<<"w:"<<w<<" width:"<<width<<endl;
                            cout<<"h:"<<h<<" height:"<<height<<endl;
                            boxes.push_back(cv::Rect(left, top, width, height));
                        }
                    }
                    data += 85; //跳过85个元素，到下一个检测框
                    
     }
     std::vector<int> nms_result; //nms结果
	cv::dnn::NMSBoxes(boxes, confidences, 0.2, 0.2, nms_result); //非极大值抑制
     for(int i = 0; i < nms_result.size(); i++){
                    int index = nms_result[i];
                    cv::Rect box = boxes[index];
                    cv::rectangle(input, box, cv::Scalar(0, 255, 0), 2);
                    cv::putText(input, this->classNames[class_ids[index]] + ": " + to_string(confidences[index]), cv::Point(box.x, box.y - 5), cv::FONT_HERSHEY_SIMPLEX, 0.5, cv::Scalar(0, 255, 0), 2);
     }
     std::vector<int> params {cv::IMWRITE_JPEG_QUALITY, imageQos};
     std::vector<uchar> encoded;
     cv::imencode(imagecode, input(Rect(0, 0, input.cols, input.rows)), encoded, params);

     auto result = std::make_unique<ImageStruct>();
    // 修复类型转换问题：使用正确的智能指针构造方式
    result->data.reset(new uchar[encoded.size()]);  // 恢复原始正确写法
    memcpy(result->data.get(), encoded.data(), encoded.size());
    result->size = encoded.size();
    
    
    return result;

}