@file:JvmName("TaskUuid")

package com.teslaflash.myapplication

import com.microsoft.thrifty.Struct
import com.microsoft.thrifty.TType
import com.microsoft.thrifty.ThriftField
import com.microsoft.thrifty.kotlin.Adapter
import com.microsoft.thrifty.protocol.Protocol
import com.microsoft.thrifty.util.ProtocolUtil
import kotlin.String
import kotlin.Unit
import kotlin.jvm.JvmField
import kotlin.jvm.JvmName

public data class TaskUuid(
  @JvmField
  @ThriftField(fieldId = 1)
  public val uuidString: String?
) : Struct {
  public override fun write(protocol: Protocol): Unit {
    ADAPTER.write(protocol, this)
  }

  private class TaskUuidAdapter : Adapter<TaskUuid> {
    public override fun read(protocol: Protocol): TaskUuid {
      var _local_uuidString: String? = null
      protocol.readStructBegin()
      while (true) {
        val fieldMeta = protocol.readFieldBegin()
        if (fieldMeta.typeId == TType.STOP) {
          break
        }
        when (fieldMeta.fieldId.toInt()) {
          1 -> {
            if (fieldMeta.typeId == TType.STRING) {
              val uuidString = protocol.readString()
              _local_uuidString = uuidString
            } else {
              ProtocolUtil.skip(protocol, fieldMeta.typeId)
            }
          }
          else -> ProtocolUtil.skip(protocol, fieldMeta.typeId)
        }
        protocol.readFieldEnd()
      }
      protocol.readStructEnd()
      return TaskUuid(uuidString = _local_uuidString)
    }

    public override fun write(protocol: Protocol, struct: TaskUuid): Unit {
      protocol.writeStructBegin("TaskUuid")
      if (struct.uuidString != null) {
        protocol.writeFieldBegin("uuidString", 1, TType.STRING)
        protocol.writeString(struct.uuidString)
        protocol.writeFieldEnd()
      }
      protocol.writeFieldStop()
      protocol.writeStructEnd()
    }
  }

  public companion object {
    @JvmField
    public val ADAPTER: Adapter<TaskUuid> = TaskUuidAdapter()
  }
}
