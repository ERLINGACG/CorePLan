

class Home{


    isFixed = false;
    scrollThreshold = 35;


    handleScroll() { //处理滚动事件

        const scrollY = window.scrollY || window.pageYOffset;
        if (scrollY > this.scrollThreshold && !this.isFixed) {
            this.headerContainer.style.position = 'fixed';
            this.headerContainer.style.top = '0';
            // this.headerContainer.style.zIndex = '1000';
            this.isFixed = true;
        } else if (scrollY <= this.scrollThreshold && this.isFixed) {
            this.headerContainer.style.position = 'absolute';
            this.headerContainer.style.top = '35px';
            this.isFixed = false;
        }
    }


    SetLeftBar(container){
        this.headerContainer = container;
        container.style.top="5%";

        window.addEventListener('scroll', this.handleScroll.bind(this));

    }
    SetCenter(container){

    }

}

export default Home;