using System.Xml;
using System.Xml.Serialization;
using System.Collections.Generic;

namespace kr.co.bcu.propio.util.config.model
{
    [XmlRootAttribute(ElementName = "PropioConfig", IsNullable = false)]
    public class DynamicXmlModel
    {
        [XmlAnyElement]
        public List<XmlNode> obj { get; set; }

        public DynamicXmlModel()
        {
        }
        public DynamicXmlModel(List<XmlNode> obj)
        {
            this.obj = obj;
        }
    }
}
