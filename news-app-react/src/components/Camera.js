import React, { PureComponent, useState } from 'react';
import { StyleSheet, Text, TouchableOpacity, View, Dimensions, Image, Alert } from 'react-native';
import { RNCamera } from 'react-native-camera';
import { Skeleton, VStack, NativeBaseProvider, HStack } from 'native-base';
import ajax from '../utils/fetch';
import axios from 'axios';
import api from '../utils/api';
const { width: screenWidth, height: screenHeight } = Dimensions.get('window');

const PendingView = () => (
  <NativeBaseProvider>
     <VStack w={screenWidth} h={screenHeight} borderWidth="1" space={8} overflow="hidden" _dark={{
      borderColor: "coolGray.500"
    }} _light={{
      borderColor: "coolGray.200"
    }}>
        <Skeleton h={screenHeight} w={screenWidth} />
      </VStack>
    </NativeBaseProvider>
);

const ExampleApp = () => {

    const [flashMode, setFlashMode] = useState(RNCamera.Constants.FlashMode.off)

    const takePicture = async (camera) => {
        const options = { quality: 0.5, base64: true };
        const data = await camera.takePictureAsync(options);
        //  eslint-disable-next-line
        const f = data.base64
        // var arr = f.split(','), mime = arr[0].match(/:(.*?);/)[1],
        //     bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
        // while(n--){
        //   u8arr[n] = bstr.charCodeAt(n);
        // }
        // const file = new File([u8arr], 'filename.jpg', { type: mime });
        const formData = new FormData();
        // console.log(data)
        formData.append('base', encodeURI(f));
        ajax({
            method: 'POST',
            url: api() + '/backend/upload',
            data: formData,
            headers: {
              'Content-Type': 'application/json',
            },
            success: (res) => {
              if (res.success) {
                alert("图中的文字包含：" + res.data)
              }
            },
            error: (err) => {
              alert(err);
            }
        });
    };
    const toggleFlash = (camera) => {
        setFlashMode(flashMode === RNCamera.Constants.FlashMode.on ? RNCamera.Constants.FlashMode.off : RNCamera.Constants.FlashMode.on)
    }
    return (
      <View style={styles.container}>
        <RNCamera 
          style={styles.preview}
          type={RNCamera.Constants.Type.back}
          flashMode={flashMode}
          androidCameraPermissionOptions={{
            title: 'Permission to use camera',
            message: 'We need your permission to use your camera',
            buttonPositive: 'Ok',
            buttonNegative: 'Cancel',
          }}
          androidRecordAudioPermissionOptions={{
            title: 'Permission to use audio recording',
            message: 'We need your permission to use your audio',
            buttonPositive: 'Ok',
            buttonNegative: 'Cancel',
          }}
        >
          {({ camera, status, recordAudioPermissionStatus }) => {
            if (status !== 'READY') return <PendingView />;
            return (
              <View style={{ flex: 0, flexDirection: 'row', justifyContent: 'center' }}>
                <TouchableOpacity onPress={() => takePicture(camera)} style={styles.capture}>
                    <Image source={require('../../assets/images/camera-click.png')} style={styles.cameraClick} />
                </TouchableOpacity>
              </View>
            );
          }}
        </RNCamera>
      </View>
    );
}
const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: 'column',
    backgroundColor: 'black',
  },
  preview: {
    flex: 1,
    justifyContent: 'flex-end',
    alignItems: 'center',
  },
  capture: {
    flex: 0,
    backgroundColor: '#fff',
    borderRadius: 5,
    padding: 15,
    paddingHorizontal: 20,
    alignSelf: 'center',
    margin: 20,
  },
  cameraClick: {
    width: 30,
    height: 30,
  }
});

export default ExampleApp;
