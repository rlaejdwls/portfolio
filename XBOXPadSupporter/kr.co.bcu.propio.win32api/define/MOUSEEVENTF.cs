namespace kr.co.bcu.propio.win32api.define
{
    public class MOUSEEVENTF
    {
        public const uint ABSOLUTE           = 0x8000; 
        public const uint HWHEEL             = 0x01000; 
        public const uint MOVE               = 0x0001; 
        public const uint MOVE_NOCOALESCE    = 0x2000; 
        public const uint LEFTDOWN           = 0x0002; 
        public const uint LEFTUP             = 0x0004; 
        public const uint RIGHTDOWN          = 0x0008; 
        public const uint RIGHTUP            = 0x0010; 
        public const uint MIDDLEDOWN         = 0x0020; 
        public const uint MIDDLEUP           = 0x0040; 
        public const uint VIRTUALDESK        = 0x4000; 
        public const uint WHEEL              = 0x0800; 
        public const uint XDOWN              = 0x0080; 
        public const uint XUP                = 0x0100;
    }
}
