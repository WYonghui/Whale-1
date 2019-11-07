/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.storm.generated;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)")
public class SpoutStats implements org.apache.thrift.TBase<SpoutStats, SpoutStats._Fields>, java.io.Serializable, Cloneable, Comparable<SpoutStats> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SpoutStats");

  private static final org.apache.thrift.protocol.TField ACKED_FIELD_DESC = new org.apache.thrift.protocol.TField("acked", org.apache.thrift.protocol.TType.MAP, (short)1);
  private static final org.apache.thrift.protocol.TField FAILED_FIELD_DESC = new org.apache.thrift.protocol.TField("failed", org.apache.thrift.protocol.TType.MAP, (short)2);
  private static final org.apache.thrift.protocol.TField COMPLETE_MS_AVG_FIELD_DESC = new org.apache.thrift.protocol.TField("complete_ms_avg", org.apache.thrift.protocol.TType.MAP, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SpoutStatsStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SpoutStatsTupleSchemeFactory());
  }

  private Map<String,Map<String,Long>> acked; // required
  private Map<String,Map<String,Long>> failed; // required
  private Map<String,Map<String,Double>> complete_ms_avg; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ACKED((short)1, "acked"),
    FAILED((short)2, "failed"),
    COMPLETE_MS_AVG((short)3, "complete_ms_avg");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ACKED
          return ACKED;
        case 2: // FAILED
          return FAILED;
        case 3: // COMPLETE_MS_AVG
          return COMPLETE_MS_AVG;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ACKED, new org.apache.thrift.meta_data.FieldMetaData("acked", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)))));
    tmpMap.put(_Fields.FAILED, new org.apache.thrift.meta_data.FieldMetaData("failed", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)))));
    tmpMap.put(_Fields.COMPLETE_MS_AVG, new org.apache.thrift.meta_data.FieldMetaData("complete_ms_avg", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SpoutStats.class, metaDataMap);
  }

  public SpoutStats() {
  }

  public SpoutStats(
    Map<String,Map<String,Long>> acked,
    Map<String,Map<String,Long>> failed,
    Map<String,Map<String,Double>> complete_ms_avg)
  {
    this();
    this.acked = acked;
    this.failed = failed;
    this.complete_ms_avg = complete_ms_avg;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SpoutStats(SpoutStats other) {
    if (other.is_set_acked()) {
      Map<String,Map<String,Long>> __this__acked = new HashMap<String,Map<String,Long>>(other.acked.size());
      for (Map.Entry<String, Map<String,Long>> other_element : other.acked.entrySet()) {

        String other_element_key = other_element.getKey();
        Map<String,Long> other_element_value = other_element.getValue();

        String __this__acked_copy_key = other_element_key;

        Map<String,Long> __this__acked_copy_value = new HashMap<String,Long>(other_element_value);

        __this__acked.put(__this__acked_copy_key, __this__acked_copy_value);
      }
      this.acked = __this__acked;
    }
    if (other.is_set_failed()) {
      Map<String,Map<String,Long>> __this__failed = new HashMap<String,Map<String,Long>>(other.failed.size());
      for (Map.Entry<String, Map<String,Long>> other_element : other.failed.entrySet()) {

        String other_element_key = other_element.getKey();
        Map<String,Long> other_element_value = other_element.getValue();

        String __this__failed_copy_key = other_element_key;

        Map<String,Long> __this__failed_copy_value = new HashMap<String,Long>(other_element_value);

        __this__failed.put(__this__failed_copy_key, __this__failed_copy_value);
      }
      this.failed = __this__failed;
    }
    if (other.is_set_complete_ms_avg()) {
      Map<String,Map<String,Double>> __this__complete_ms_avg = new HashMap<String,Map<String,Double>>(other.complete_ms_avg.size());
      for (Map.Entry<String, Map<String,Double>> other_element : other.complete_ms_avg.entrySet()) {

        String other_element_key = other_element.getKey();
        Map<String,Double> other_element_value = other_element.getValue();

        String __this__complete_ms_avg_copy_key = other_element_key;

        Map<String,Double> __this__complete_ms_avg_copy_value = new HashMap<String,Double>(other_element_value);

        __this__complete_ms_avg.put(__this__complete_ms_avg_copy_key, __this__complete_ms_avg_copy_value);
      }
      this.complete_ms_avg = __this__complete_ms_avg;
    }
  }

  public SpoutStats deepCopy() {
    return new SpoutStats(this);
  }

  @Override
  public void clear() {
    this.acked = null;
    this.failed = null;
    this.complete_ms_avg = null;
  }

  public int get_acked_size() {
    return (this.acked == null) ? 0 : this.acked.size();
  }

  public void put_to_acked(String key, Map<String,Long> val) {
    if (this.acked == null) {
      this.acked = new HashMap<String,Map<String,Long>>();
    }
    this.acked.put(key, val);
  }

  public Map<String,Map<String,Long>> get_acked() {
    return this.acked;
  }

  public void set_acked(Map<String,Map<String,Long>> acked) {
    this.acked = acked;
  }

  public void unset_acked() {
    this.acked = null;
  }

  /** Returns true if field acked is set (has been assigned a value) and false otherwise */
  public boolean is_set_acked() {
    return this.acked != null;
  }

  public void set_acked_isSet(boolean value) {
    if (!value) {
      this.acked = null;
    }
  }

  public int get_failed_size() {
    return (this.failed == null) ? 0 : this.failed.size();
  }

  public void put_to_failed(String key, Map<String,Long> val) {
    if (this.failed == null) {
      this.failed = new HashMap<String,Map<String,Long>>();
    }
    this.failed.put(key, val);
  }

  public Map<String,Map<String,Long>> get_failed() {
    return this.failed;
  }

  public void set_failed(Map<String,Map<String,Long>> failed) {
    this.failed = failed;
  }

  public void unset_failed() {
    this.failed = null;
  }

  /** Returns true if field failed is set (has been assigned a value) and false otherwise */
  public boolean is_set_failed() {
    return this.failed != null;
  }

  public void set_failed_isSet(boolean value) {
    if (!value) {
      this.failed = null;
    }
  }

  public int get_complete_ms_avg_size() {
    return (this.complete_ms_avg == null) ? 0 : this.complete_ms_avg.size();
  }

  public void put_to_complete_ms_avg(String key, Map<String,Double> val) {
    if (this.complete_ms_avg == null) {
      this.complete_ms_avg = new HashMap<String,Map<String,Double>>();
    }
    this.complete_ms_avg.put(key, val);
  }

  public Map<String,Map<String,Double>> get_complete_ms_avg() {
    return this.complete_ms_avg;
  }

  public void set_complete_ms_avg(Map<String,Map<String,Double>> complete_ms_avg) {
    this.complete_ms_avg = complete_ms_avg;
  }

  public void unset_complete_ms_avg() {
    this.complete_ms_avg = null;
  }

  /** Returns true if field complete_ms_avg is set (has been assigned a value) and false otherwise */
  public boolean is_set_complete_ms_avg() {
    return this.complete_ms_avg != null;
  }

  public void set_complete_ms_avg_isSet(boolean value) {
    if (!value) {
      this.complete_ms_avg = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ACKED:
      if (value == null) {
        unset_acked();
      } else {
        set_acked((Map<String,Map<String,Long>>)value);
      }
      break;

    case FAILED:
      if (value == null) {
        unset_failed();
      } else {
        set_failed((Map<String,Map<String,Long>>)value);
      }
      break;

    case COMPLETE_MS_AVG:
      if (value == null) {
        unset_complete_ms_avg();
      } else {
        set_complete_ms_avg((Map<String,Map<String,Double>>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ACKED:
      return get_acked();

    case FAILED:
      return get_failed();

    case COMPLETE_MS_AVG:
      return get_complete_ms_avg();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ACKED:
      return is_set_acked();
    case FAILED:
      return is_set_failed();
    case COMPLETE_MS_AVG:
      return is_set_complete_ms_avg();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SpoutStats)
      return this.equals((SpoutStats)that);
    return false;
  }

  public boolean equals(SpoutStats that) {
    if (that == null)
      return false;

    boolean this_present_acked = true && this.is_set_acked();
    boolean that_present_acked = true && that.is_set_acked();
    if (this_present_acked || that_present_acked) {
      if (!(this_present_acked && that_present_acked))
        return false;
      if (!this.acked.equals(that.acked))
        return false;
    }

    boolean this_present_failed = true && this.is_set_failed();
    boolean that_present_failed = true && that.is_set_failed();
    if (this_present_failed || that_present_failed) {
      if (!(this_present_failed && that_present_failed))
        return false;
      if (!this.failed.equals(that.failed))
        return false;
    }

    boolean this_present_complete_ms_avg = true && this.is_set_complete_ms_avg();
    boolean that_present_complete_ms_avg = true && that.is_set_complete_ms_avg();
    if (this_present_complete_ms_avg || that_present_complete_ms_avg) {
      if (!(this_present_complete_ms_avg && that_present_complete_ms_avg))
        return false;
      if (!this.complete_ms_avg.equals(that.complete_ms_avg))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_acked = true && (is_set_acked());
    list.add(present_acked);
    if (present_acked)
      list.add(acked);

    boolean present_failed = true && (is_set_failed());
    list.add(present_failed);
    if (present_failed)
      list.add(failed);

    boolean present_complete_ms_avg = true && (is_set_complete_ms_avg());
    list.add(present_complete_ms_avg);
    if (present_complete_ms_avg)
      list.add(complete_ms_avg);

    return list.hashCode();
  }

  @Override
  public int compareTo(SpoutStats other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(is_set_acked()).compareTo(other.is_set_acked());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_acked()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.acked, other.acked);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_failed()).compareTo(other.is_set_failed());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_failed()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.failed, other.failed);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(is_set_complete_ms_avg()).compareTo(other.is_set_complete_ms_avg());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (is_set_complete_ms_avg()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.complete_ms_avg, other.complete_ms_avg);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("SpoutStats(");
    boolean first = true;

    sb.append("acked:");
    if (this.acked == null) {
      sb.append("null");
    } else {
      sb.append(this.acked);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("failed:");
    if (this.failed == null) {
      sb.append("null");
    } else {
      sb.append(this.failed);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("complete_ms_avg:");
    if (this.complete_ms_avg == null) {
      sb.append("null");
    } else {
      sb.append(this.complete_ms_avg);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!is_set_acked()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'acked' is unset! Struct:" + toString());
    }

    if (!is_set_failed()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'failed' is unset! Struct:" + toString());
    }

    if (!is_set_complete_ms_avg()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'complete_ms_avg' is unset! Struct:" + toString());
    }

    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class SpoutStatsStandardSchemeFactory implements SchemeFactory {
    public SpoutStatsStandardScheme getScheme() {
      return new SpoutStatsStandardScheme();
    }
  }

  private static class SpoutStatsStandardScheme extends StandardScheme<SpoutStats> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SpoutStats struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ACKED
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map260 = iprot.readMapBegin();
                struct.acked = new HashMap<String,Map<String,Long>>(2*_map260.size);
                String _key261;
                Map<String,Long> _val262;
                for (int _i263 = 0; _i263 < _map260.size; ++_i263)
                {
                  _key261 = iprot.readString();
                  {
                    org.apache.thrift.protocol.TMap _map264 = iprot.readMapBegin();
                    _val262 = new HashMap<String,Long>(2*_map264.size);
                    String _key265;
                    long _val266;
                    for (int _i267 = 0; _i267 < _map264.size; ++_i267)
                    {
                      _key265 = iprot.readString();
                      _val266 = iprot.readI64();
                      _val262.put(_key265, _val266);
                    }
                    iprot.readMapEnd();
                  }
                  struct.acked.put(_key261, _val262);
                }
                iprot.readMapEnd();
              }
              struct.set_acked_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // FAILED
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map268 = iprot.readMapBegin();
                struct.failed = new HashMap<String,Map<String,Long>>(2*_map268.size);
                String _key269;
                Map<String,Long> _val270;
                for (int _i271 = 0; _i271 < _map268.size; ++_i271)
                {
                  _key269 = iprot.readString();
                  {
                    org.apache.thrift.protocol.TMap _map272 = iprot.readMapBegin();
                    _val270 = new HashMap<String,Long>(2*_map272.size);
                    String _key273;
                    long _val274;
                    for (int _i275 = 0; _i275 < _map272.size; ++_i275)
                    {
                      _key273 = iprot.readString();
                      _val274 = iprot.readI64();
                      _val270.put(_key273, _val274);
                    }
                    iprot.readMapEnd();
                  }
                  struct.failed.put(_key269, _val270);
                }
                iprot.readMapEnd();
              }
              struct.set_failed_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // COMPLETE_MS_AVG
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map276 = iprot.readMapBegin();
                struct.complete_ms_avg = new HashMap<String,Map<String,Double>>(2*_map276.size);
                String _key277;
                Map<String,Double> _val278;
                for (int _i279 = 0; _i279 < _map276.size; ++_i279)
                {
                  _key277 = iprot.readString();
                  {
                    org.apache.thrift.protocol.TMap _map280 = iprot.readMapBegin();
                    _val278 = new HashMap<String,Double>(2*_map280.size);
                    String _key281;
                    double _val282;
                    for (int _i283 = 0; _i283 < _map280.size; ++_i283)
                    {
                      _key281 = iprot.readString();
                      _val282 = iprot.readDouble();
                      _val278.put(_key281, _val282);
                    }
                    iprot.readMapEnd();
                  }
                  struct.complete_ms_avg.put(_key277, _val278);
                }
                iprot.readMapEnd();
              }
              struct.set_complete_ms_avg_isSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, SpoutStats struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.acked != null) {
        oprot.writeFieldBegin(ACKED_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.MAP, struct.acked.size()));
          for (Map.Entry<String, Map<String,Long>> _iter284 : struct.acked.entrySet())
          {
            oprot.writeString(_iter284.getKey());
            {
              oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.I64, _iter284.getValue().size()));
              for (Map.Entry<String, Long> _iter285 : _iter284.getValue().entrySet())
              {
                oprot.writeString(_iter285.getKey());
                oprot.writeI64(_iter285.getValue());
              }
              oprot.writeMapEnd();
            }
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.failed != null) {
        oprot.writeFieldBegin(FAILED_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.MAP, struct.failed.size()));
          for (Map.Entry<String, Map<String,Long>> _iter286 : struct.failed.entrySet())
          {
            oprot.writeString(_iter286.getKey());
            {
              oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.I64, _iter286.getValue().size()));
              for (Map.Entry<String, Long> _iter287 : _iter286.getValue().entrySet())
              {
                oprot.writeString(_iter287.getKey());
                oprot.writeI64(_iter287.getValue());
              }
              oprot.writeMapEnd();
            }
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.complete_ms_avg != null) {
        oprot.writeFieldBegin(COMPLETE_MS_AVG_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.MAP, struct.complete_ms_avg.size()));
          for (Map.Entry<String, Map<String,Double>> _iter288 : struct.complete_ms_avg.entrySet())
          {
            oprot.writeString(_iter288.getKey());
            {
              oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.DOUBLE, _iter288.getValue().size()));
              for (Map.Entry<String, Double> _iter289 : _iter288.getValue().entrySet())
              {
                oprot.writeString(_iter289.getKey());
                oprot.writeDouble(_iter289.getValue());
              }
              oprot.writeMapEnd();
            }
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SpoutStatsTupleSchemeFactory implements SchemeFactory {
    public SpoutStatsTupleScheme getScheme() {
      return new SpoutStatsTupleScheme();
    }
  }

  private static class SpoutStatsTupleScheme extends TupleScheme<SpoutStats> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SpoutStats struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      {
        oprot.writeI32(struct.acked.size());
        for (Map.Entry<String, Map<String,Long>> _iter290 : struct.acked.entrySet())
        {
          oprot.writeString(_iter290.getKey());
          {
            oprot.writeI32(_iter290.getValue().size());
            for (Map.Entry<String, Long> _iter291 : _iter290.getValue().entrySet())
            {
              oprot.writeString(_iter291.getKey());
              oprot.writeI64(_iter291.getValue());
            }
          }
        }
      }
      {
        oprot.writeI32(struct.failed.size());
        for (Map.Entry<String, Map<String,Long>> _iter292 : struct.failed.entrySet())
        {
          oprot.writeString(_iter292.getKey());
          {
            oprot.writeI32(_iter292.getValue().size());
            for (Map.Entry<String, Long> _iter293 : _iter292.getValue().entrySet())
            {
              oprot.writeString(_iter293.getKey());
              oprot.writeI64(_iter293.getValue());
            }
          }
        }
      }
      {
        oprot.writeI32(struct.complete_ms_avg.size());
        for (Map.Entry<String, Map<String,Double>> _iter294 : struct.complete_ms_avg.entrySet())
        {
          oprot.writeString(_iter294.getKey());
          {
            oprot.writeI32(_iter294.getValue().size());
            for (Map.Entry<String, Double> _iter295 : _iter294.getValue().entrySet())
            {
              oprot.writeString(_iter295.getKey());
              oprot.writeDouble(_iter295.getValue());
            }
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SpoutStats struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TMap _map296 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.MAP, iprot.readI32());
        struct.acked = new HashMap<String,Map<String,Long>>(2*_map296.size);
        String _key297;
        Map<String,Long> _val298;
        for (int _i299 = 0; _i299 < _map296.size; ++_i299)
        {
          _key297 = iprot.readString();
          {
            org.apache.thrift.protocol.TMap _map300 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.I64, iprot.readI32());
            _val298 = new HashMap<String,Long>(2*_map300.size);
            String _key301;
            long _val302;
            for (int _i303 = 0; _i303 < _map300.size; ++_i303)
            {
              _key301 = iprot.readString();
              _val302 = iprot.readI64();
              _val298.put(_key301, _val302);
            }
          }
          struct.acked.put(_key297, _val298);
        }
      }
      struct.set_acked_isSet(true);
      {
        org.apache.thrift.protocol.TMap _map304 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.MAP, iprot.readI32());
        struct.failed = new HashMap<String,Map<String,Long>>(2*_map304.size);
        String _key305;
        Map<String,Long> _val306;
        for (int _i307 = 0; _i307 < _map304.size; ++_i307)
        {
          _key305 = iprot.readString();
          {
            org.apache.thrift.protocol.TMap _map308 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.I64, iprot.readI32());
            _val306 = new HashMap<String,Long>(2*_map308.size);
            String _key309;
            long _val310;
            for (int _i311 = 0; _i311 < _map308.size; ++_i311)
            {
              _key309 = iprot.readString();
              _val310 = iprot.readI64();
              _val306.put(_key309, _val310);
            }
          }
          struct.failed.put(_key305, _val306);
        }
      }
      struct.set_failed_isSet(true);
      {
        org.apache.thrift.protocol.TMap _map312 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.MAP, iprot.readI32());
        struct.complete_ms_avg = new HashMap<String,Map<String,Double>>(2*_map312.size);
        String _key313;
        Map<String,Double> _val314;
        for (int _i315 = 0; _i315 < _map312.size; ++_i315)
        {
          _key313 = iprot.readString();
          {
            org.apache.thrift.protocol.TMap _map316 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.DOUBLE, iprot.readI32());
            _val314 = new HashMap<String,Double>(2*_map316.size);
            String _key317;
            double _val318;
            for (int _i319 = 0; _i319 < _map316.size; ++_i319)
            {
              _key317 = iprot.readString();
              _val318 = iprot.readDouble();
              _val314.put(_key317, _val318);
            }
          }
          struct.complete_ms_avg.put(_key313, _val314);
        }
      }
      struct.set_complete_ms_avg_isSet(true);
    }
  }

}

