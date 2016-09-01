namespace kr.co.bcu.propio.util.control.questionbox.model
{
    [System.Xml.Serialization.XmlRoot("Help")]
    public class HelpModel
    {
        [System.Xml.Serialization.XmlElement]
        public System.Collections.Generic.List<PageNode> Page { get; set; }

        [System.Xml.Serialization.XmlAttribute]
        public bool IsLock { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public int SelectPage { get; set; }

        [System.Xml.Serialization.XmlAttribute]
        public int X { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public int Y { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public int Width { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public int Height { get; set; }
    }
}
