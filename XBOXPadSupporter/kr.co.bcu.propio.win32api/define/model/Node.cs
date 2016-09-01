namespace kr.co.bcu.propio.win32api.define.model
{
    public class Node
    {
        [System.Xml.Serialization.XmlAttribute]
        public string Display { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string Const { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string Value { get; set; }
    }
}
