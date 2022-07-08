import React, {useState, useRef, useEffect} from 'react';
import {
  TouchableOpacity,
  Text,
  View,
  Alert,
  StyleSheet,
  Dimensions,
  Image,
  StatusBar,
  Platform,
  Button,
} from 'react-native';
import ScrollableTabView, {
  DefaultTabBar,
  ScrollableTabBar,
} from 'react-native-scrollable-tab-view';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs';
import Swiper from 'react-native-swiper';
import ajax from '../utils/fetch';
import api from '../utils/api';
import MyTab from './Tab';
import Mine from './Mine';

import {isLT19} from '../utils/ScreenUtil';

const {width: screenWidth, height: screenHeight} = Dimensions.get('window');


const Home = props => {
  const [swiperData, setSwiperData] = useState([
    '华为不看好5G',
    '陶渊明后人做主播',
    '尔康制药遭处罚',
    '卢恩光行贿一案受审',
    '盖茨力挺扎克伯格',
    '大连特大刑事案件',
    '高校迷香盗窃案',
    '少年被批评后溺亡',
    '北京工商约谈抖音',
  ]);

  useEffect(() => {
    ajax({
      url: api() + '/backend/showTechNews',
      success: data => {
        const resData = data.data;
        const arr = [];
        try {
          resData.forEach(element => {
            if (element.title.length > 10) {
              arr.push(element.title.substring(0, 10) + '...');
            }
          });
          setSwiperData(arr)
        } catch (error) {
          console.log(error);
        }
      },
      error: err => {
        alert('网络异常');
      },
    });
    // return null;
  }, []);

  const {navigation} = props;
  const ScrollRef = useRef('ScrollRef');
  
  const IndexComponent = () => {
    return (
      <View style={styles.container}>
        {/* 状态栏 */}
        <StatusBar backgroundColor={'rgba(255,255,255, 0)'} translucent={true} style={styles.statusBar} />
  
        {/* 首页搜索栏 */}
        <View style={styles.headerContainer}>
          <TouchableOpacity
            activeOpacity={1}
            onPress={() => {
              alert('This is News App Logo!');
            }}>
            <Image
              source={require('./../../assets/images/news-fff.png')}
              resizeMode={'contain'}
              style={styles.headerLogo}
            />
          </TouchableOpacity>
          <View style={styles.headerSearchContainer}>
            <Swiper
              horizontal={false}
              autoplay={true}
              showsPagination={false}
              scrollEnabled={false}
              autoplayTimeout={5}>
              {swiperData.map((item, index) => {
                return (
                  <TouchableOpacity
                    activeOpacity={1}
                    key={index}
                    style={styles.swiperItem}
                    onPress={() => {
                      alert('hello');
                    }}>
                    <Image
                      source={require('./../../assets/images/i_search.png')}
                      resizeMode={'contain'}
                      style={styles.headerSearchImg}
                    />
                    <Text style={styles.headerSearchText}>{item}</Text>
                  </TouchableOpacity>
                );
              })}
            </Swiper>
          </View>
          <TouchableOpacity
            activeOpacity={1}
            onPress={() => {
              navigation.navigate('Camera');
            }}>
            <Image
              source={require('./../../assets/images/camera-white.png')}
              resizeMode={'contain'}
              style={styles.headerRightImg}
            />
          </TouchableOpacity>
        </View>
  
        {/* 新闻栏目 */}
        <View style={styles.container}>
          <View style={styles.columnSelect}>
            <Image
              source={require('./../../assets/images/i_menu.png')}
              style={{width: 20, height: 20}}
            />
          </View>
          <MyTab navigation={navigation} />
        </View>
      </View>
    )
  }
    const MessageScreen = () => {
        return (
          <View style={styles.container}>
            <Text>title</Text>
          </View>
        );
    };
  
    const Tab = createBottomTabNavigator();

    return (
        <Tab.Navigator
            screenOptions={({ route }) => ({
              tabBarIcon: ({ focused, color, size }) => {

                if (route.name === '首页') {
                  if (focused) {
                    return <Image source={require('../../assets/images/home.png')} style={styles.icon}></Image>
                  } else {
                    return <Image source={require('../../assets/images/home-white.png')} style={styles.icon}></Image>
                  }
                } else if (route.name === '用户') {
                  if (focused) {
                    return <Image source={require('../../assets/images/user.png')} style={styles.icon}></Image>
                  } else {
                    return <Image source={require('../../assets/images/user-white.png')} style={styles.icon}></Image>
                  }
                }
              },
              tabBarActiveTintColor: '#d81e06',
              tabBarInactiveTintColor: 'gray',
            })}
        >
            <Tab.Screen
                name="首页"
                component={IndexComponent}
                options={{headerStyle: {backgroundColor: '#d81e06', height: 40}}}
            />
            <Tab.Screen
              name="用户"
              options={{headerStyle: {backgroundColor: '#d81e06', height: 40}}}
              component={Mine}
            />
        </Tab.Navigator>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F8F8F8',
    position: 'relative',
  },
  headerContainer: {
    flexDirection: 'row',
    backgroundColor: '#d81e06',
    justifyContent: 'space-between',
    alignItems: 'center',
    height: Platform.OS === 'ios' ? 80 : 70,
    paddingTop: isLT19() ? 0 : 25,
    paddingBottom: 5,
    paddingHorizontal: 8,
  },
  headerLogo: {
    width: 45,
    height: 45,
  },
  headerSearchContainer: {
    marginBottom: 3,
    width: screenWidth * 0.65,
    height: 33,
    borderRadius: 18,
    backgroundColor: 'rgba(255,255,255,.3)',
  },
  headerRightImg: {
    width: 27,
    height: 27,
  },
  swiperItem: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
  },
  headerSearchImg: {
    width: 17,
    height: 17,
    marginRight: 5,
  },
  headerSearchText: {
    color: 'rgba(248,248,248,.6)',
  },

  columnSelect: {
    justifyContent: 'center',
    alignItems: 'center',
    position: 'absolute',
    width: screenWidth * 0.1,
    height: 50,
    top: 0,
    right: 0,
  },
  icon: {
    width: 25,
    height: 25,
  }
});

export default Home;
