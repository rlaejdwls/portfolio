using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;

namespace kr.co.bcu.propio.util.config.model
{
    public class ConfigModel : Hashtable
    {
        public List<object> getElementList(string elementName)
        {
            List<object> abcd = getElementList(elementName, this.GetEnumerator());
            return abcd;
        }
        public List<object> getElementList(string elementName, Hashtable map)
        {
            List<object> abcd = getElementList(elementName, map.GetEnumerator());
            return abcd;
        }
        public List<object> getElementList(string elementName, IDictionaryEnumerator ide)
        {
            while (ide.MoveNext())
            {
                if (ide.Value is List<object>)
                {
                    if (ide.Key.Equals(elementName)) { return (List<object>)ide.Value; }

                    List<object> objList = (List<object>)ide.Value;
                    foreach (object obj in objList)
                    {
                        if (obj is Hashtable)
                        {
                            List<object> result = getElementList(elementName, ((Hashtable)obj).GetEnumerator());
                            if (result != null) return result;
                        }
                    }
                }
                else if (ide.Value is Hashtable)
                {
                    List<object> result = getElementList(elementName, ((Hashtable)ide.Value).GetEnumerator());
                    if (result != null) return result;
                }
            }
            return null;
        }

        public Hashtable getElementMap(string elementName)
        {
            Hashtable abcd = getElementMap(elementName, this.GetEnumerator());
            return abcd;
        }
        public Hashtable getElementMap(string elementName, Hashtable map)
        {
            Hashtable abcd = getElementMap(elementName, map.GetEnumerator());
            return abcd;
        }
        public Hashtable getElementMap(string elementName, IDictionaryEnumerator ide)
        {
            while (ide.MoveNext())
            {
                if (ide.Value is List<object>)
                {
                    List<object> objList = (List<object>)ide.Value;

                    foreach (object obj in objList)
                    {
                        if (obj is Hashtable)
                        {
                            Hashtable result = getElementMap(elementName, ((Hashtable)obj).GetEnumerator());
                            if (result != null) return result;
                        }
                    }
                }
                else if (ide.Value is Hashtable)
                {
                    if (ide.Key.Equals(elementName)) { return (Hashtable) ide.Value; }

                    Hashtable result = getElementMap(elementName, ((Hashtable)ide.Value).GetEnumerator());
                    if (result != null) return result;
                }
            }
            return null;
        }

        public object getElementValue(string elementName)
        {
            object abcd = getElementValue(elementName, this.GetEnumerator());
            return abcd;
        }
        public object getElementValue(string elementName, Hashtable map)
        {
            object abcd = getElementValue(elementName, map.GetEnumerator());
            return abcd;
        }
        public object getElementValue(string elementName, IDictionaryEnumerator ide)
        {
            while (ide.MoveNext())
            {
                if (ide.Value is List<object>)
                {
                    List<object> objList = (List<object>)ide.Value;

                    foreach (object obj in objList)
                    {
                        if (obj is Hashtable)
                        {
                            object result = getElementValue(elementName, ((Hashtable)obj).GetEnumerator());
                            if (result != null) return result;
                        }
                    }
                }
                else if (ide.Value is Hashtable)
                {
                    object result = getElementValue(elementName, ((Hashtable)ide.Value).GetEnumerator());
                    if (result != null) return result;
                }
                else
                {
                    if (ide.Key.Equals(elementName)) { return ide.Value; }
                }
            }
            return null;
        }

        public string ToString(string key)
        {
            if (this[key] != null)
            {
                return this[key].ToString();
            }
            else
            {
                return "";
            }
        }
        public string ToString(string key, string defaultValue)
        {
            if (this.ContainsKey(key)) return this[key].ToString();
            else return defaultValue;
        }
        public int ToInteger(string key)
        {
            return int.Parse(this[key].ToString());
        }
        public int ToInteger(string key, int defaultValue)
        {
            if (this.ContainsKey(key)) return int.Parse(this[key].ToString());
            else return defaultValue;
        }
        public float ToFloat(string key)
        {
            return float.Parse(this[key].ToString());
        }
        public bool ToBool(string key)
        {
            return bool.Parse(this[key].ToString());
        }
        public bool ToBool(string key, bool defaultValue)
        {
            if (this.ContainsKey(key)) return bool.Parse(this[key].ToString());
            else return defaultValue;
        }
    }
}
