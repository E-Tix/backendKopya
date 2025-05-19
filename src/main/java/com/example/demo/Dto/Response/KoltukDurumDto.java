package com.example.demo.Dto.Response;

public class KoltukDurumDto {
    private Long koltukId;
    private int koltukNumarasi;
    private boolean doluMu;

    // getter, setter, constructor

    public KoltukDurumDto(Long koltukId, int koltukNumarasi, boolean doluMu) {
        this.koltukId = koltukId;
        this.koltukNumarasi = koltukNumarasi;
        this.doluMu = doluMu;
    }
    public Long getKoltukId() {
        return koltukId;
    }
    public void setKoltukId(Long koltukId) {
        this.koltukId = koltukId;
    }
    public int getKoltukNo() {
        return koltukNumarasi;
    }
    public void setKoltukNo(int koltukNo) {
        this.koltukNumarasi = koltukNo;
    }
    public boolean isDoluMu() {
        return doluMu;
    }
    public void setDoluMu(boolean doluMu) {
        this.doluMu = doluMu;
    }
}
