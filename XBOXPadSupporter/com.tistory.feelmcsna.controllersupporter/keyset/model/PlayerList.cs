namespace com.tistory.feelmcsna.controllersupporter.keyset.model
{
    public class PlayerList
    {
        [System.Xml.Serialization.XmlAttribute]
        public int Num { get; set; }
        [System.Xml.Serialization.XmlElement]
        public ButtonSet ButtonSet { get; set; }
        [System.Xml.Serialization.XmlElement]
        public DPadSet DPadSet { get; set; }
        [System.Xml.Serialization.XmlElement]
        public StickSet StickSet { get; set; }
        [System.Xml.Serialization.XmlElement]
        public TriggerSet TriggerSet { get; set; }

        public PlayerList()
        {
            Num = 1;
            ButtonSet = new ButtonSet();
            DPadSet = new DPadSet();
            StickSet = new StickSet();
            TriggerSet = new TriggerSet();
        }
    }
}
