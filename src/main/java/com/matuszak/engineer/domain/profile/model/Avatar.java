package com.matuszak.engineer.domain.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Avatar {

    private String data;

//    @Column(name = "avatar")
//    @Lob
//    @Basic(fetch = FetchType.LAZY)
//    private byte[] data;
//
//    public Avatar(File file){
//        this.setAvatar(file);
//    }
//
//    private void setAvatar(File file){
//        try(FileInputStream fileInputStream = new FileInputStream(file)) {
//            this.data = new byte[(int) file.length()];
//            fileInputStream.read(this.data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
