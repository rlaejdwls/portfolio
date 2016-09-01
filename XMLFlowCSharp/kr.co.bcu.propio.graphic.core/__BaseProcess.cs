using kr.co.bcu.propio.graphic.core.evento.type;
using kr.co.bcu.propio.graphic.core.manage;

namespace kr.co.bcu.propio.graphic.core
{
    public class __BaseProcess
    {
        public __BaseProcess() { initialize(this.GetType().Name + "UiBinder"); }
        public __BaseProcess(string binderName) { initialize(binderName + "UiBinder"); }

        private void initialize(string binderName)
        {
            //System.Windows.Forms.MessageBox.Show(binderName);
        }

        public virtual void onCreate() { }
        public virtual void onExecute() { }
        public virtual void onDestroy() { }

        public virtual void onNext(string id)
        {
            Controller.getInstance().nextProcess(id);
        }

        public virtual void onDraw(System.Drawing.Graphics g)
        {
            g.DrawLine(new System.Drawing.Pen(System.Drawing.Color.Black), 0, 0, 100, 100);
        }

        public virtual void onEvent(object sender, System.EventArgs e, EventType type) { }

        public virtual void onActionDownEvent(object sender, System.EventArgs e) { }
        public virtual void onActionMoveEvent(object sender, System.EventArgs e) { }
        public virtual void onActionUpEvent(object sender, System.EventArgs e) { }
    }
}
