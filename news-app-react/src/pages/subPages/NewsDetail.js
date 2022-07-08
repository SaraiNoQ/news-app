import React, {PureComponent, useEffect, useState} from 'react';
import {
  Platform,
  Dimensions,
  StyleSheet,
  Text,
  Button,
  View,
  Alert,
  Image,
  ScrollView,
  Modal,
  TouchableOpacity,
  StatusBar,
} from 'react-native';
import { Skeleton, VStack, NativeBaseProvider, HStack } from 'native-base';
import ajax from './../../utils/fetch';
import api from './../../utils/api';
import Header from './../../components/Header';

const { width: screenWidth, height: screenHeight } = Dimensions.get('window');

const Throb = () => {
  return (
    <NativeBaseProvider>
     <VStack w="100%" borderWidth="1" space={8} overflow="hidden" rounded="md" _dark={{
      borderColor: "coolGray.500"
    }} _light={{
      borderColor: "coolGray.200"
    }}>
        <Skeleton h="40" />
        <Skeleton.Text px="4" />
        <Skeleton px="4" my="4" rounded="md" startColor="primary.100" />
      </VStack>
    </NativeBaseProvider>
  )
};

const NewsDetail = ({ route, navigation}) => {
    const { title } = route.params
    const [isMounted, setIsMounted] = useState(true)
    useEffect(() => {
        ajax({
            url: api() + '/backend/showNewsContent?title=' + title,
            success: data => {
                if (isMounted) {
                    const resData = data.data
                    const arr = resData.content.split('\r').map(item => item.replace(/\n/g, '')).filter(item => item!=='')
                
                    setNewsData({
                        title: resData.title,
                        content: arr,
                        ptime: resData.created_date || '2022-07-06',
                        author: resData.author || 'admin',
                    });
                }
            },
            error: err => {
                if (isMounted) {
                    alert(err);
                }
            }
        })
        return () => {
            setIsMounted(false);
        }
    }, [])
    const [newsData, setNewsData] = useState({
        author: '',
        ptime: '',
        content: [],
        title: '加载中...',
    })
    return (
        <View style={styles.container}>
            <StatusBar backgroundColor={'rgba(255,255,255, 0)'} translucent={true} />
            <Header navigation={navigation} />
            <ScrollView
            >
                <View style={{ padding: 10 }}>
                    <Text style={{ fontSize: 22, fontWeight: 'bold', color: '#2c2c2c', marginBottom: 10, lineHeight: 35 }}>{newsData.title}</Text>
                    <Text>{newsData.author}  {newsData.ptime}</Text>
                </View>
                <View style={styles.contentContainer}>
                    {newsData.content.map((item, index) => {
                        return <Text style={styles.content} key={item}>{item}</Text>
                    })}
                </View>
            </ScrollView>
        </View>
    )
}

const styles = StyleSheet.create({

    container: {
        flex: 1,
        backgroundColor: '#F8F8F8'
    },
    contentContainer: {
        marginVertical: 10,
    },
    content: {
        fontSize: 18,
        lineHeight: 30,
        color: '#2c2c2c',
        marginHorizontal: 10,
        marginBottom: 10,
    }


});

const htmlStyles = StyleSheet.create({

    p: {
        color: '#2c2c2c',
        lineHeight: 30,
        fontSize:16
    }

});

export default NewsDetail;
