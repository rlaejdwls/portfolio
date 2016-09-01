namespace kr.co.bcu.propio.win32api.define
{
    public class VK
    {
        /*
         * Virtual Keys, Standard Set
         */
        public const byte LBUTTON        = 0x01;
        public const byte RBUTTON        = 0x02;
        public const byte CANCEL         = 0x03;
        public const byte MBUTTON        = 0x04;    /* NOT contiguous with L & RBUTTON */
        public const byte XBUTTON1       = 0x05;    /* NOT contiguous with L & RBUTTON */
        public const byte XBUTTON2       = 0x06;    /* NOT contiguous with L & RBUTTON */
        public const byte BACK           = 0x08;
        public const byte TAB            = 0x09;
        /*
         * 0x0A - 0x0B : reserved
         */
        public const byte CLEAR          = 0x0C;
        public const byte RETURN         = 0x0D;
        public const byte SHIFT          = 0x10;
        public const byte CONTROL        = 0x11;
        public const byte MENU           = 0x12;
        public const byte PAUSE          = 0x13;
        public const byte CAPITAL        = 0x14;
        public const byte KANA           = 0x15;
        public const byte HANGEUL        = 0x15;  /* old name - should be here for compatibility */
        public const byte HANGUL         = 0x15;
        public const byte JUNJA          = 0x17;
        public const byte FINAL          = 0x18;
        public const byte HANJA          = 0x19;
        public const byte KANJI          = 0x19;
        public const byte ESCAPE         = 0x1B;
        public const byte CONVERT        = 0x1C;
        public const byte NONCONVERT     = 0x1D;
        public const byte ACCEPT         = 0x1E;
        public const byte MODECHANGE     = 0x1F;
        public const byte SPACE          = 0x20;
        public const byte PRIOR          = 0x21;
        public const byte NEXT           = 0x22;
        public const byte END            = 0x23;
        public const byte HOME           = 0x24;
        public const byte LEFT           = 0x25;
        public const byte UP             = 0x26;
        public const byte RIGHT          = 0x27;
        public const byte DOWN           = 0x28;
        public const byte SELECT         = 0x29;
        public const byte PRINT          = 0x2A;
        public const byte EXECUTE        = 0x2B;
        public const byte SNAPSHOT       = 0x2C;
        public const byte INSERT         = 0x2D;
        public const byte DELETE         = 0x2E;
        public const byte HELP           = 0x2F;
        /*
         * VK_0 - VK_9 are the same as ASCII '0' - '9' (0x30 - 0x39)
         * 0x40 : unassigned
         * VK_A - VK_Z are the same as ASCII 'A' - 'Z' (0x41 - 0x5A)
         */
        public const byte LWIN           = 0x5B;
        public const byte RWIN           = 0x5C;
        public const byte APPS           = 0x5D;
        /*
         * 0x5E : reserved
         */
        public const byte SLEEP          = 0x5F;
        public const byte NUMPAD0        = 0x60;
        public const byte NUMPAD1        = 0x61;
        public const byte NUMPAD2        = 0x62;
        public const byte NUMPAD3        = 0x63;
        public const byte NUMPAD4        = 0x64;
        public const byte NUMPAD5        = 0x65;
        public const byte NUMPAD6        = 0x66;
        public const byte NUMPAD7        = 0x67;
        public const byte NUMPAD8        = 0x68;
        public const byte NUMPAD9        = 0x69;
        public const byte MULTIPLY       = 0x6A;
        public const byte ADD            = 0x6B;
        public const byte SEPARATOR      = 0x6C;
        public const byte SUBTRACT       = 0x6D;
        public const byte DECIMAL        = 0x6E;
        public const byte DIVIDE         = 0x6F;
        public const byte F1             = 0x70;
        public const byte F2             = 0x71;
        public const byte F3             = 0x72;
        public const byte F4             = 0x73;
        public const byte F5             = 0x74;
        public const byte F6             = 0x75;
        public const byte F7             = 0x76;
        public const byte F8             = 0x77;
        public const byte F9             = 0x78;
        public const byte F10            = 0x79;
        public const byte F11            = 0x7A;
        public const byte F12            = 0x7B;
        public const byte F13            = 0x7C;
        public const byte F14            = 0x7D;
        public const byte F15            = 0x7E;
        public const byte F16            = 0x7F;
        public const byte F17            = 0x80;
        public const byte F18            = 0x81;
        public const byte F19            = 0x82;
        public const byte F20            = 0x83;
        public const byte F21            = 0x84;
        public const byte F22            = 0x85;
        public const byte F23            = 0x86;
        public const byte F24            = 0x87;
        /*
         * 0x88 - 0x8F : unassigned
         */
        public const byte NUMLOCK        = 0x90;
        public const byte SCROLL         = 0x91;
        /*
         * NEC PC-9800 kbd definitions
         */
        public const byte OEM_NEC_EQUAL  = 0x92;   // '=' key on numpad
        /*
         * Fujitsu/OASYS kbd definitions
         */
        public const byte OEM_FJ_JISHO   = 0x92;   // 'Dictionary' key
        public const byte OEM_FJ_MASSHOU = 0x93;   // 'Unregister word' key
        public const byte OEM_FJ_TOUROKU = 0x94;   // 'Register word' key
        public const byte OEM_FJ_LOYA    = 0x95;   // 'Left OYAYUBI' key
        public const byte OEM_FJ_ROYA    = 0x96;   // 'Right OYAYUBI' key
        /*
         * 0x97 - 0x9F : unassigned
         */

        /*
         * VK_L* & VK_R* - left and right Alt, Ctrl and Shift virtual keys.
         * Used only as parameters to GetAsyncKeyState() and GetKeyState().
         * No other API or message will distinguish left and right keys in this way.
         */
        public const byte LSHIFT         = 0xA0;
        public const byte RSHIFT         = 0xA1;
        public const byte LCONTROL       = 0xA2;
        public const byte RCONTROL       = 0xA3;
        public const byte LMENU          = 0xA4;
        public const byte RMENU          = 0xA5;
        public const byte BROWSER_BACK        = 0xA6;
        public const byte BROWSER_FORWARD     = 0xA7;
        public const byte BROWSER_REFRESH     = 0xA8;
        public const byte BROWSER_STOP        = 0xA9;
        public const byte BROWSER_SEARCH      = 0xAA;
        public const byte BROWSER_FAVORITES   = 0xAB;
        public const byte BROWSER_HOME        = 0xAC;
        public const byte VOLUME_MUTE         = 0xAD;
        public const byte VOLUME_DOWN         = 0xAE;
        public const byte VOLUME_UP           = 0xAF;
        public const byte MEDIA_NEXT_TRACK    = 0xB0;
        public const byte MEDIA_PREV_TRACK    = 0xB1;
        public const byte MEDIA_STOP          = 0xB2;
        public const byte MEDIA_PLAY_PAUSE    = 0xB3;
        public const byte LAUNCH_MAIL         = 0xB4;
        public const byte LAUNCH_MEDIA_SELECT = 0xB5;
        public const byte LAUNCH_APP1         = 0xB6;
        public const byte LAUNCH_APP2         = 0xB7;
        /*
         * 0xB8 - 0xB9 : reserved
         */
        public const byte OEM_1          = 0xBA;   // ';:' for US
        public const byte OEM_PLUS       = 0xBB;   // '+' any country
        public const byte OEM_COMMA      = 0xBC;   // ',' any country
        public const byte OEM_MINUS      = 0xBD;   // '-' any country
        public const byte OEM_PERIOD     = 0xBE;   // '.' any country
        public const byte OEM_2          = 0xBF;   // '/?' for US
        public const byte OEM_3          = 0xC0;   // '`~' for US
        /*
         * 0xC1 - 0xD7 : reserved
         */
        /*
         * 0xD8 - 0xDA : unassigned
         */
        public const byte OEM_4          = 0xDB;  //  '[{' for US
        public const byte OEM_5          = 0xDC;  //  '\|' for US
        public const byte OEM_6          = 0xDD;  //  ']}' for US
        public const byte OEM_7          = 0xDE;  //  ''"' for US
        public const byte OEM_8          = 0xDF;
        /*
         * 0xE0 : reserved
         */
        /*
         * Various extended or enhanced keyboards
         */
        public const byte OEM_AX         = 0xE1;  //  'AX' key on Japanese AX kbd
        public const byte OEM_102        = 0xE2;  //  "<>" or "\|" on RT 102-key kbd.
        public const byte ICO_HELP       = 0xE3;  //  Help key on ICO
        public const byte ICO_00         = 0xE4;  //  00 key on ICO
        public const byte PROCESSKEY     = 0xE5;
        public const byte ICO_CLEAR      = 0xE6;
        public const byte PACKET         = 0xE7;
        /*
         * 0xE8 : unassigned
         */
        /*
         * Nokia/Ericsson definitions
         */
        public const byte OEM_RESET      = 0xE9;
        public const byte OEM_JUMP       = 0xEA;
        public const byte OEM_PA1        = 0xEB;
        public const byte OEM_PA2        = 0xEC;
        public const byte OEM_PA3        = 0xED;
        public const byte OEM_WSCTRL     = 0xEE;
        public const byte OEM_CUSEL      = 0xEF;
        public const byte OEM_ATTN       = 0xF0;
        public const byte OEM_FINISH     = 0xF1;
        public const byte OEM_COPY       = 0xF2;
        public const byte OEM_AUTO       = 0xF3;
        public const byte OEM_ENLW       = 0xF4;
        public const byte OEM_BACKTAB    = 0xF5;
        public const byte ATTN           = 0xF6;
        public const byte CRSEL          = 0xF7;
        public const byte EXSEL          = 0xF8;
        public const byte EREOF          = 0xF9;
        public const byte PLAY           = 0xFA;
        public const byte ZOOM           = 0xFB;
        public const byte NONAME         = 0xFC;
        public const byte PA1            = 0xFD;
        public const byte OEM_CLEAR      = 0xFE;
    }
}
