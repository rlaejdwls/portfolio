using System;
using System.Collections.Generic;
using System.Drawing;
using System.Runtime.CompilerServices;
using kr.co.bcu.propio.graphic.core.evento.type;

namespace kr.co.bcu.propio.graphic.core.manage
{
    public class Controller
    {
        private static Controller controller;
        private kr.co.bcu.propio.graphic.core.baseflow.FlowManager flow =
            new kr.co.bcu.propio.graphic.core.baseflow.FlowManager();

        private Controller() 
        {
            setThisProcess(flow.initialize());
        }

        [MethodImpl(MethodImplOptions.Synchronized)]
        public static Controller getInstance()
        {
            if (controller == null)
            {
                controller = new Controller();
            }
            return controller;
        }

        private __BaseProcess thisProcess;
        private List<__BasePopup> popupList = null;

        public void setThisProcess(__BaseProcess process)
        {
            if (thisProcess != null) { thisProcess.onDestroy(); }
            thisProcess = process;
            thisProcess.onCreate();
            thisProcess.onExecute();
        }
        public __BaseProcess getThisProcess()
        {
            return thisProcess;
        }

        public void nextProcess(string id)
        {
            setThisProcess(flow.nextFlowline(id));
        }

        public void onUpdate(Graphics g)
        {
            g.Clear(Color.White);
            thisProcess.onDraw(g);
        }

        public void onEvent(object sender, EventArgs e, EventType type)
        {
            thisProcess.onEvent(sender, e, type);
            //switch (type)
            //{
            //    case EventType.ACTION_DOWN:
            //        thisProcess.onActionDownEvent(sender, e);
            //        break;
            //    case EventType.ACTION_MOVE:
            //        thisProcess.onActionMoveEvent(sender, e);
            //        break;
            //    case EventType.ACTION_UP:
            //        thisProcess.onActionUpEvent(sender, e);
            //        break;
            //    default:
            //        break;
            //}
        }
    }
}
