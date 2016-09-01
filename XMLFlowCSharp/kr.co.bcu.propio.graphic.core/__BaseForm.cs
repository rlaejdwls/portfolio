using kr.co.bcu.propio.graphic.core.evento.ievent;
using kr.co.bcu.propio.graphic.core.evento;
using kr.co.bcu.propio.graphic.core.evento.type;

namespace kr.co.bcu.propio.graphic.core
{
    public class __BaseForm : __BaseProcess
    {
        private OnEventHandler onEventHandler;

        //private OnActionDownEventHandler onActionDownEventHandler;
        //private OnActionMoveEventHandler onActionMoveEventHandler;
        //private OnActionUpEventHandler onActionUpEventHandler;

        public void setEventHandler(OnEventHandler handler)
        {
            this.onEventHandler = handler;
        }
        //public void setActionDownEventHandler(OnActionDownEventHandler handler)
        //{
        //    this.onActionDownEventHandler = handler;
        //}
        //public void setActionMoveEventHandler(OnActionMoveEventHandler handler)
        //{
        //    this.onActionMoveEventHandler = handler;
        //}
        //public void setActionUpEventHandler(OnActionUpEventHandler handler)
        //{
        //    this.onActionUpEventHandler = handler;
        //}

        public override void onEvent(object sender, System.EventArgs e, EventType type)
        {
            if (onEventHandler != null)
            {
                System.Collections.Hashtable tmpEventMap = new System.Collections.Hashtable();
                tmpEventMap.Add(EventType.ACTION_CLICK, "button1_onActionClick");
                tmpEventMap.Add(EventType.ACTION_DOUBLE_CLICK, "button1_onActionDoubleClick");
                tmpEventMap.Add(EventType.ACTION_DOWN, "button1_onActionDown");
                tmpEventMap.Add(EventType.ACTION_MOVE, "button1_onActionMove");
                tmpEventMap.Add(EventType.ACTION_UP, "button1_onActionUp");

                if (tmpEventMap.ContainsKey(type))
                {
                    string methodName = tmpEventMap[type].ToString();

                    try
                    {
                        System.Reflection.MethodInfo mi = onEventHandler.GetType().GetMethod(methodName);
                        mi.Invoke(onEventHandler, new object[] { sender, e });
                    }
                    catch (System.NullReferenceException nre)
                    {
                        System.Windows.Forms.MessageBox.Show("[" + onEventHandler.GetType().FullName + "]클래스에" +
                            " [" + methodName + "] 함수를 정의하세요.\n" + nre.ToString());
                    }
                    catch (System.Exception ex)
                    {
                        System.Windows.Forms.MessageBox.Show(ex.ToString());
                    }
                }
            }
        }
        //public override void onActionDownEvent(object sender, System.EventArgs e)
        //{
        //    if (onActionDownEventHandler != null)
        //    {
        //        string methodName = "button1_onActionDown";

        //        try
        //        {
        //            System.Reflection.MethodInfo mi = onActionDownEventHandler.GetType().GetMethod(methodName);
        //            mi.Invoke(onActionDownEventHandler, new object[] { sender, e });
        //        }
        //        catch (System.NullReferenceException nre)
        //        {
        //            System.Windows.Forms.MessageBox.Show("[ERROR][" + onActionDownEventHandler.GetType().FullName + "]클래스에" +
        //                " [" + methodName + "] 함수를 정의하세요.\n" + nre.ToString());
        //        }
        //        catch (System.Exception ex)
        //        {
        //            System.Windows.Forms.MessageBox.Show(ex.ToString());
        //        }
        //    }
        //}
        //public override void onActionMoveEvent(object sender, System.EventArgs e)
        //{
        //    if (onActionMoveEventHandler != null)
        //    {
        //        string methodName = "button1_onActionMove";

        //        try
        //        {
        //            System.Reflection.MethodInfo mi = onActionMoveEventHandler.GetType().GetMethod(methodName);
        //            mi.Invoke(onActionMoveEventHandler, new object[] { sender, e });
        //        }
        //        catch (System.NullReferenceException nre)
        //        {
        //            System.Windows.Forms.MessageBox.Show("[ERROR][" + onActionMoveEventHandler.GetType().FullName + "]클래스에" +
        //                " [" + methodName + "] 함수를 정의하세요.\n" + nre.ToString());
        //        }
        //        catch (System.Exception ex)
        //        {
        //            System.Windows.Forms.MessageBox.Show(ex.ToString());
        //        }
        //    }
        //}
        //public override void onActionUpEvent(object sender, System.EventArgs e)
        //{
        //    if (onActionUpEventHandler != null)
        //    {
        //        string methodName = "button1_onActionUp";

        //        try
        //        {
        //            System.Reflection.MethodInfo mi = onActionUpEventHandler.GetType().GetMethod(methodName);
        //            mi.Invoke(onActionUpEventHandler, new object[] { sender, e });
        //        }
        //        catch (System.NullReferenceException nre)
        //        {
        //            System.Windows.Forms.MessageBox.Show("[ERROR][" + onActionUpEventHandler.GetType().FullName + "]클래스에" +
        //                " [" + methodName + "] 함수를 정의하세요.\n" + nre.ToString());
        //        }
        //        catch (System.Exception ex)
        //        {
        //            System.Windows.Forms.MessageBox.Show(ex.ToString());
        //        }
        //    }
        //}
    }
}
