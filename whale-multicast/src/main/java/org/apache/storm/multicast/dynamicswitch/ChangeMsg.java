package org.apache.storm.multicast.dynamicswitch;

/**
 * locate org.apache.storm.multicast.dynamicswitch
 * Created by master on 2019/10/31.
 */
public class ChangeMsg {
    private String disconnect = null;
    private String reconnect = null;
    private Integer id = null;
    private Integer layer = null;
    private Integer index = null;

    @Override
    public String toString() {
        return (disconnect == null ? "null" : disconnect) + " " + (reconnect == null ? "null" : reconnect) + " " + (id == null ? "null" : id) + " " + (layer == null ? "null" : layer) + " " + (index == null ? "null" : index);
    }

    public boolean shouldUpdate() {
        return shouldReconnect() || shouldMetaUpdate();
    }

    public boolean shouldReconnect() {
        return !(disconnect == null && reconnect == null);
    }

    public boolean shouldMetaUpdate() {
        return id != null || layer != null || index != null;
    }

    public ChangeMsg setDisconnect(String disconnect) {
        this.disconnect = disconnect;
        if (this.disconnect.equals(this.reconnect)) {
            this.disconnect = null;
            this.reconnect = null;
        }
        return this;
    }

    public ChangeMsg setReconnect(String reconnect) {
        this.reconnect = reconnect;
        if (this.reconnect.equals(this.disconnect)) {
            this.reconnect = null;
            this.disconnect = null;
        }
        return this;
    }

    public ChangeMsg setId(Integer id) {
        this.id = id;
        return this;
    }

    public ChangeMsg setIndex(Integer index) {
        this.index = index;
        return this;
    }

    public ChangeMsg setLayer(Integer layer) {
        this.layer = layer;
        return this;
    }

    public ChangeMsg setMeta(Integer id, Integer layer, Integer index) {
        return setId(id).setLayer(layer).setIndex(index);
    }

    public String getDisconnect() {
        return disconnect;
    }

    public String getReconnect() {
        return reconnect;
    }

    public Integer getId() {
        return id;
    }

    public Integer getLayer() {
        return layer;
    }

    public Integer getIndex() {
        return index;
    }
}
