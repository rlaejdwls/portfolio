namespace com.tistory.feelmcsna.controllersupporter.keyset.model
{
    public class ButtonSet
    {
        [System.Xml.Serialization.XmlAttribute]
        public string LeftShoulder { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string RightShoulder { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string LeftStick { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string RightStick { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string Back { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string Start { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string A { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string B { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string X { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string Y { get; set; }

        public ButtonSet()
        {
            LeftShoulder = "LeftShift";
            RightShoulder = "f";
            LeftStick = "e";
            RightStick = "1";
            Back = "";
            Start = "Esc";
            A = "a";
            B = "b";
            X = "x";
            Y = "y";
        }
    }
}
