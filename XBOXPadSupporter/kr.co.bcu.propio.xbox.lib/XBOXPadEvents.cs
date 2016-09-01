using kr.co.bcu.propio.xbox.lib.handler;
using kr.co.bcu.propio.xbox.lib.handler.model;

namespace kr.co.bcu.propio.xbox.lib
{
    public class XBOXPadEvents
    {
        /// <summary>
        /// XBOX 패드의 상태를 주기적으로 검사하여 이벤트를 발생시키는 스레드
        /// </summary>
        private System.Threading.Thread eventCallThread;

        /// <summary>
        /// 이벤트 콜백 스레드의 동작 주기를 가져오거나 설정합니다.
        /// </summary>
        public int CallbackFrame { get; set; }
        /// <summary>
        /// 이벤트 콜백 스레드의 동작 여부를 가져오거나 설정합니다.
        /// </summary>
        public bool IsCallbackThreading { get; set; }

        public XBOXPadEvents()
        {
            CallbackFrame = 15;
            IsCallbackThreading = true;

            eventCallThread = new System.Threading.Thread(new System.Threading.ThreadStart(XBOXPadEventCallback));
            eventCallThread.Start();
        }

        private void XBOXPadEventCallback()
        {
            while (IsCallbackThreading)
            {
                try
                {
                    XBOXPadButtonEventArgs btnArgs = new XBOXPadButtonEventArgs();
                    btnArgs.ButtonInfo = Microsoft.Xna.Framework.Input.GamePad
                        .GetState(Microsoft.Xna.Framework.PlayerIndex.One).Buttons;

                    if (ButtonEvent != null)
                    {
                        ButtonEvent(btnArgs);
                    }

                    XBOXPadDPadEventArgs dpadArgs = new XBOXPadDPadEventArgs();
                    dpadArgs.DPadInfo = Microsoft.Xna.Framework.Input.GamePad
                        .GetState(Microsoft.Xna.Framework.PlayerIndex.One).DPad;

                    if (DPadEvent != null)
                    {
                        DPadEvent(dpadArgs);
                    }

                    XBOXPadStickEventArgs stickArgs = new XBOXPadStickEventArgs();
                    stickArgs.StickInfo = Microsoft.Xna.Framework.Input.GamePad
                        .GetState(Microsoft.Xna.Framework.PlayerIndex.One).ThumbSticks;

                    if (StickEvent != null)
                    {
                        StickEvent(stickArgs);
                    }

                    XBOXPadTriggerEventArgs triggerArgs = new XBOXPadTriggerEventArgs();
                    triggerArgs.TriggersInfo = Microsoft.Xna.Framework.Input.GamePad
                        .GetState(Microsoft.Xna.Framework.PlayerIndex.One).Triggers;

                    if (TriggerEvent != null)
                    {
                        TriggerEvent(triggerArgs);
                    }
                }
                catch (System.Exception e)
                {
                    System.Console.WriteLine(e.Message);
                }
                System.Threading.Thread.Sleep(1000 / CallbackFrame);
            }
        }

        /// <summary>
        /// 버튼 이벤트
        /// </summary>
        public event XBOXPadButtonEventHandler ButtonEvent;
        /// <summary>
        /// DPad 이벤트
        /// </summary>
        public event XBOXPadDPadEventHandler DPadEvent;
        /// <summary>
        /// 스틱 이벤트
        /// </summary>
        public event XBOXPadStickEventHandler StickEvent;
        /// <summary>
        /// 트리거 이벤트
        /// </summary>
        public event XBOXPadTriggerEventHandler TriggerEvent;
        /// <summary>
        /// 버튼이 눌렸을 때 발생되는 이벤트
        /// </summary>
        public event XBOXPadButtonPressedEventHandler ButtonPressed;
    }
}
