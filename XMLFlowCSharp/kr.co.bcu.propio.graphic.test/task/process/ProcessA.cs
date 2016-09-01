using kr.co.bcu.propio.graphic.core;
using kr.co.bcu.propio.graphic.core.evento.ievent;
using kr.co.bcu.propio.graphic.core.evento;

namespace kr.co.bcu.propio.graphic.test.task.process
{
    public class ProcessA : __BaseForm
    {
        private GraphicExampleCSharp.data.BaseShape shape = new GraphicExampleCSharp.data.BaseShape();

        internal class ProcessAEvent
            : OnEventHandler
        {
            private ProcessA outer;

            public ProcessAEvent(ProcessA outer)
            {
                this.outer = outer;
            }

            private bool downFlag = false;

            public void button1_onActionClick(object sender, System.Windows.Forms.MouseEventArgs e)
            {
            }

            public void button1_onActionDoubleClick(object sender, System.Windows.Forms.MouseEventArgs e)
            {
                ((__BaseMain)sender).Close();
            }

            public void button1_onActionDown(object sender, System.Windows.Forms.MouseEventArgs e)
            {
                if (outer.shape.getPtInRect(e.X, e.Y) && !downFlag)
                {
                    outer.shape.setDownX(e.X);
                    outer.shape.setDownY(e.Y);
                    downFlag = true;
                }
            }

            public void button1_onActionMove(object sender, System.Windows.Forms.MouseEventArgs e)
            {
                if (downFlag)
                {
                    outer.shape.move(e.X, e.Y);
                }
            }

            public void button1_onActionUp(object sender, System.Windows.Forms.MouseEventArgs e)
            {
                if (downFlag) downFlag = false;
            }
        }

        public ProcessA()
        {
        }

        public override void onCreate()
        {
            ProcessAEvent eventA = new ProcessAEvent(this);
            this.setEventHandler(eventA);
        }

        public override void onDestroy()
        {
        }

        public override void onDraw(System.Drawing.Graphics g)
        {
            g.DrawImage(new System.Drawing.Bitmap(System.Windows.Forms.Application.StartupPath + "\\etc\\img\\1.jpg"), 0, 0);
            shape.onDraw(g);
        }
    }
}
