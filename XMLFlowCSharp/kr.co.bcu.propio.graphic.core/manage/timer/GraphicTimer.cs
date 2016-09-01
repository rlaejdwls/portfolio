using System;
using System.Timers;
using System.Drawing;
using kr.co.bcu.propio.util.config;

namespace kr.co.bcu.propio.graphic.core.manage.timer
{
    /*
     * GraphicTimer 
     *  - 일정 간격으로 화면을 Update
     *  - controller를 통하여 모든 작업을 수행
     */
    public class GraphicTimer : Timer
    {
        private bool isUpdate;

        private Controller controller;
        private Graphics g;
        private Graphics bufG;      //Buffer GDI
        private Bitmap bufBmp;      //Buffer Canvas

        public GraphicTimer(Graphics g, int width, int height)
        {
            this.Elapsed += new ElapsedEventHandler(this.run);
            this.Interval = 1000 / int.Parse(__BaseConfig.getConfig("CoreConfig")["FramePerSecond"].ToString());

            controller = Controller.getInstance();
            this.g = g;

            /*
             * Double buffering에 관한 내용
             * ! : Bitmap 크기 동적으로 수정해야 한다.
             */
            bufBmp = new Bitmap(width, height);        //View와 크기가 같은 Canvas를 생성한다.
            bufG = Graphics.FromImage(bufBmp);      //Buffer Canvas의 GDI를 가져온다.
            isUpdate = true;                        //기본 Update 사용으로 세팅1
        }

        public void setGraphics(Graphics g, int width, int height)
        {
            this.g = g;
            bufBmp = new Bitmap(width, height);
            bufG = Graphics.FromImage(bufBmp);
        }

        public void run(object sender, ElapsedEventArgs e)
        {
            if (isUpdate)
            {
                lock (g)
                {
                    try
                    {
                        controller.onUpdate(bufG);      //Buffer GDI를 이용해 Buffer Canvas에 Graphic 작업을 한다.
                        g.DrawImage(bufBmp, 0, 0);      //Buffer Canvas의 내용을 한번에 출력한다.
                    }
                    catch (Exception ex)
                    {
                        ex.ToString();
                    }
                }
            }
        }
    }
}
