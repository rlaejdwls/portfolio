namespace com.tistory.feelmcsna.controllersupporter.keyset.model
{
    [System.Xml.Serialization.XmlRoot]
    public class KeySet
    {
        [System.Xml.Serialization.XmlElement]
        public System.Collections.Generic.List<PlayerList> PlayerList { get; set; }

        public KeySet()
        {
            PlayerList = new System.Collections.Generic.List<PlayerList>();
        }
    }
}
