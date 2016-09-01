namespace com.tistory.feelmcsna.controllersupporter.keyset.model
{
    public class TriggerSet
    {
        [System.Xml.Serialization.XmlAttribute]
        public string Left { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public string Right { get; set; }
        [System.Xml.Serialization.XmlElement]
        public Setting Setting { get; set; }

        public TriggerSet()
        {
            Left = "MouseLeftClick";
            Right = "MouseRightClick";

            Setting = new Setting();
        }
    }
}
