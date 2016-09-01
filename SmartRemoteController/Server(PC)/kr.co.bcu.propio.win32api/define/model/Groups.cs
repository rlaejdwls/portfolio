namespace kr.co.bcu.propio.win32api.define.model
{
    public class Groups
    {
        [System.Xml.Serialization.XmlElement]
        public string Display { get; set; }
        [System.Xml.Serialization.XmlElement]
        public System.Collections.Generic.List<Node> Node { get; set; }
    }
}
