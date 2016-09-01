using System.Xml.Serialization;

namespace kr.co.bcu.propio.graphic.core.baseflow.model
{
    public class FlowFlowlineNode
    {
        [XmlAttribute(AttributeName = "id")]
        public string FlowlineID { get; set; }

        [XmlAttribute(AttributeName = "next-terminal")]
        public string NextTerminal { get; set; }

        [XmlAttribute(AttributeName = "next-process")]
        public string NextProcess { get; set; }

        [XmlAttribute(AttributeName = "desc")]
        public string Desc { get; set; }

        public void initialize()
        {
        }
    }
}
