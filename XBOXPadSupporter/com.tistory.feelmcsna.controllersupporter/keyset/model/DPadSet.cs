namespace com.tistory.feelmcsna.controllersupporter.keyset.model
{
    public class DPadSet
    {
        [System.Xml.Serialization.XmlAttribute]
        public string Up { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string Down { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string Left { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string Right { get; set; }

        public DPadSet()
        {
            Up = "KeyUp";
            Down = "KeyDown";
            Left = "KeyLeft";
            Right = "KeyRight";
        }
    }
}
