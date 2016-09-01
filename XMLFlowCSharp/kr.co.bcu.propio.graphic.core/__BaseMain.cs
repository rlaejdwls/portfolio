using kr.co.bcu.propio.graphic.core.evento.type;
using kr.co.bcu.propio.graphic.core.manage;
using kr.co.bcu.propio.graphic.core.manage.timer;
//using kr.co.sisnet.neo.tascmanager.model;
//using kr.co.sisnet.neo.tascmanager.manage;
//using kr.co.sisnet.neo.pos.core.task;
//using kr.co.sisnet.neo.tascmanager.config;

namespace kr.co.bcu.propio.graphic.core
{
    public partial class __BaseMain : System.Windows.Forms.Form
    {
        public __BaseMain()
        {
            InitializeComponent();
        }

        //__BaseForm METHOD
        private void initConfig()
        {
            //System.Reflection.FieldInfo[] fi = this.GetType().GetFields();
            //System.Reflection.PropertyInfo[] pi = this.GetType().GetProperties();

            //string propertyList = "";

            ////foreach (System.Reflection.FieldInfo f in fi)
            //foreach (System.Reflection.PropertyInfo p in pi)
            //{
            //    //propertyList += f.Name + "\n";
            //    propertyList += p.Name + "\n";
            //}

            //System.Windows.Forms.MessageBox.Show(propertyList);
            //System.Console.WriteLine(propertyList);
        }
        private void saveConfig()
        {
        }

        //__BaseForm EVENT
        private Controller controller = null;
        private GraphicTimer timer = null;

        private void __BaseMain_Load(object sender, System.EventArgs e)
        {
            //initialize
            initConfig();

            controller = Controller.getInstance();

            timer = new GraphicTimer(this.CreateGraphics(), this.ClientSize.Width, this.ClientSize.Height/*eventspace.CreateGraphics()*/);
            timer.Start();
        }
        private void __BaseMain_FormClosing(object sender, System.Windows.Forms.FormClosingEventArgs e)
        {
            saveConfig();
        }

        private void __BaseMain_MouseClick(object sender, System.Windows.Forms.MouseEventArgs e)
        {
            controller.onEvent(sender, e, EventType.ACTION_CLICK);
        }
        private void __BaseMain_MouseDoubleClick(object sender, System.Windows.Forms.MouseEventArgs e)
        {
            controller.onEvent(sender, e, EventType.ACTION_DOUBLE_CLICK);
        }
        private void __BaseMain_MouseDown(object sender, System.Windows.Forms.MouseEventArgs e)
        {
            controller.onEvent(sender, e, EventType.ACTION_DOWN);
        }
        private void __BaseMain_MouseMove(object sender, System.Windows.Forms.MouseEventArgs e)
        {
            controller.onEvent(sender, e, EventType.ACTION_MOVE);
        }
        private void __BaseMain_MouseUp(object sender, System.Windows.Forms.MouseEventArgs e)
        {
            controller.onEvent(sender, e, EventType.ACTION_UP);
        }
        
        private void __BaseMain_ResizeBegin(object sender, System.EventArgs e)
        {
            //this.Opacity = 0.5;
        }
        private void __BaseMain_Resize(object sender, System.EventArgs e)
        {
            System.Console.Write("W:" + this.Size.Width + "H:" + this.Size.Height);
            if (timer != null)
            {
                timer.setGraphics(this.CreateGraphics(), this.ClientSize.Width, this.ClientSize.Height/*eventspace.CreateGraphics(), eventspace.Width, eventspace.Height*/);
                //timer.Start();
            }
        }
        private void __BaseMain_ResizeEnd(object sender, System.EventArgs e)
        {
            //this.Opacity = 1;
        }

        private void __BaseMain_Paint(object sender, System.Windows.Forms.PaintEventArgs e)
        {
        }
    }
}