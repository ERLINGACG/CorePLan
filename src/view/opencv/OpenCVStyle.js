class OpenCVStyle {
     isFixed = false;
     scrollThreshold = 35;
     SetLeftBarStyle(ref) {
         this.headerContainer = ref;
          window.addEventListener('scroll', this.Set_handleScroll.bind(this));
     }
     Set_handleScroll() { //处理滚动事件

          const scrollY = window.scrollY || window.pageYOffset;
          if (scrollY > this.scrollThreshold && !this.isFixed) {
               this.headerContainer.style.position = 'fixed';
               this.headerContainer.style.top = '0px';
               this.isFixed = true;
          } else if (scrollY <= this.scrollThreshold && this.isFixed) {
               this.headerContainer.style.position = 'absolute';
               this.headerContainer.style.top = '35px';
               this.isFixed = false;
          }
     }
}

export default new OpenCVStyle();