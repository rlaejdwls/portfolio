namespace com.tistory.feelmcsna.controllersupporter.keyset.model
{
    public class Setting
    {
        [System.Xml.Serialization.XmlAttribute]
        public int LeftSensitivity { get; set; }
        [System.Xml.Serialization.XmlAttribute]
        public int RightSensitivity { get; set; }

        public Setting()
        {
            LeftSensitivity = 10;
            RightSensitivity = 10;
        }
    }
}
