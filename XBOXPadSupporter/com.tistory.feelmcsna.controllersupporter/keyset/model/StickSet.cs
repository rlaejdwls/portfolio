namespace com.tistory.feelmcsna.controllersupporter.keyset.model
{
    public class StickSet
    {
        [System.Xml.Serialization.XmlAttribute]
        public string Left { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string Right { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public bool IsLeftDetail { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public bool IsRightDetail { get; set; }
        [System.Xml.Serialization.XmlElement]
        public Setting Setting { get; set; }

        [System.Xml.Serialization.XmlAttribute]
        public string Left0 { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string Left1 { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string Left2 { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string Left3 { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string Right0 { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string Right1 { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string Right2 { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string Right3 { get; set; }

        public StickSet()
        {
            Left = "WASD KeySet";
            Right = "MouseMove";
            IsLeftDetail = false;
            IsRightDetail = false;

            Left0 = "A";
            Left1 = "D";
            Left2 = "W";
            Left3 = "S";
            Right0 = "KeyLeft";
            Right1 = "KeyRight";
            Right2 = "KeyUp";
            Right3 = "KeyDown";

            Setting = new Setting();
        }
    }
}
