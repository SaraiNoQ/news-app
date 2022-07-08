/**
 * Created by HuangXiaoFeng on 2018-02-08.
 */

import React, { PureComponent } from 'react';
import {
    Platform,
    Dimensions,
    StyleSheet,
    Text,
    View,
    Image,
    Alert,
    ScrollView,
    TouchableOpacity,
    Animated,
    Easing
} from 'react-native';
import { isLT19 } from '../utils/ScreenUtil'
import ListItem from '../components/ListItem'

export default class Mine extends PureComponent{

    /* 构造器 */
    constructor(props){
        super(props);
        this.state = {
            rotation: new Animated.Value(0),
            scale: new Animated.Value(1),
            translateY: new Animated.Value(10),
            opacity: new Animated.Value(0)
        };
    }

    settingData = [
        {
            leftText: '我的消息',
            rightText: '评论/通知',
            onPress(){
                alert(123);
            }
        },
        {
            leftText: '我的关注',
        },
        {
            leftText: '我的商城',
            rightText: '618特惠，好礼享不停'
        },
        {
            leftText: '活动广场',
        },
        {
            leftText: '我的钱包',
        },
        {
            leftText: '免流量看新闻',
        },
        {
            leftText: '设置',
        },
        {
            leftText: '意见反馈',
        },
        {
            leftText: '关于',
            isShowUnderline: false,
        }
    ];

    /* 渲染后 */
    componentDidMount() {

        //顺序执行
        Animated.sequence([

            Animated.timing(
                this.state.rotation,
                {
                    toValue: 1,
                    duration: 500,
                    easing: Easing.linear,// linear \ ease
                }
            ),
            Animated.timing(
                this.state.scale,
                {
                    toValue: 1.3,
                    duration: 600,
                }
            ),

            // 同时执行
            Animated.parallel([
                Animated.timing(
                    this.state.scale,
                    {
                        toValue: 1,
                        duration: 500,
                    }
                ),
                Animated.timing(
                    this.state.opacity,
                    {
                        toValue: 1,
                        duration: 1000,
                    }
                ),
                Animated.timing(
                    this.state.translateY,
                    {
                        toValue: 0,
                        duration: 600,
                    }
                ),
            ])

        ]).start();

    }

    render() {
        return (
            <ScrollView style={styles.container}>

                {/* 头部 */}
                <View style={styles.headContainer}>

                    {/* 夜间/签到 */}
                    <View style={styles.headTopContainer}>

                        <TouchableOpacity style={styles.topBtnStyle} activeOpacity={.9} onPress={()=>{alert('夜间')}}>
                            <Image source={require('./../../assets/images/i_night.png')} style={styles.headTopImg} resizeMode={'contain'}/>
                            <Text style={styles.headTopText}>夜间</Text>
                        </TouchableOpacity>

                        <TouchableOpacity style={styles.topBtnStyle} activeOpacity={.9} onPress={()=>{alert('签到')}}>
                            <Image source={require('./../../assets/images/i_sign.png')} style={styles.headTopImg} resizeMode={'contain'}/>
                            <Text style={styles.headTopText}>签到</Text>
                        </TouchableOpacity>

                    </View>

                    {/* 头像、昵称、标签 */}
                    <View style={styles.headCenterContainer}>
                        <Animated.Image style={[styles.userImg, {
                            transform:[
                                {
                                    rotateY: this.state.rotation.interpolate({
                                        inputRange: [0, 1],
                                        outputRange: ['0deg', '720deg']
                                    }),
                                },
                                {
                                    scale: this.state.scale
                                }
                            ]
                        }]} source={require('./../../assets/images/user-circl.png')} resizeMode={'contain'}/>

                        <Animated.Text style={[styles.userNickname, {opacity: this.state.opacity, transform: [

                            {
                                translateY: this.state.translateY
                            }

                        ]}]}>SaraiNoQ</Animated.Text>
                        <View style={styles.positionContainer}>
                            <Image style={styles.positionImg} source={require('./../../assets/images/i_bookmark.png')}/>
                            <Text style={styles.positionText}>你还不是VIP用户哦</Text>
                        </View>
                    </View>

                    {/* 收藏、历史、跟帖 */}
                    <View style={styles.headBottomContainer} >

                        <TouchableOpacity style={styles.bottomBtn} activeOpacity={1} onPress={()=>{alert('收藏')}}>
                            <Text style={styles.bottomNum}>2</Text>
                            <Text style={styles.bottomText}>收藏</Text>
                        </TouchableOpacity>

                        <TouchableOpacity style={styles.bottomBtn} activeOpacity={1} onPress={()=>{alert('历史')}}>
                            <Text style={styles.bottomNum}>769</Text>
                            <Text style={styles.bottomText}>历史</Text>
                        </TouchableOpacity>

                        <TouchableOpacity style={styles.bottomBtn} activeOpacity={1} onPress={()=>{alert('跟帖')}}>
                            <Text style={styles.bottomNum}>170</Text>
                            <Text style={styles.bottomText}>粉丝</Text>
                        </TouchableOpacity>
                    </View>
                </View>

                {/* 过渡条 */}
                <View style={styles.transitionView} />

                {/* 设置列表 */}
                <View style={styles.settingListContainer}>
                    {
                        this.settingData.map((item, index) => (
                            <ListItem
                                key={index}
                                leftText={item.leftText}
                                rightText={item.rightText}
                                rightComponent={item.rightComponent}
                                isShowArrow={item.isShowArrow}
                                isShowUnderline={item.isShowUnderline}
                                onPress={item.onPress}
                            />
                        ))
                    }
                </View>
            </ScrollView>
        );
    }

}

const styles = StyleSheet.create({

    container: {
        flex: 1,
        backgroundColor: '#F8F8F8'
    },
    headContainer: {
        paddingHorizontal: 15,
        paddingBottom: 10,
        paddingTop: isLT19()?10:30,
    },
    headTopContainer: {
        flexDirection: 'row',
        justifyContent: 'space-between',

    },
    topBtnStyle: {
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center',
        width: 70,
        height: 30,
        borderColor: '#e6e6e6',
        borderWidth: 1,
        borderRadius: 20,
    },
    headTopImg: {
        width: 15,
        height: 15,
        marginRight: 5
    },
    headTopText: {
        fontSize: 12,
        color: '#515151'
    },
    headCenterContainer: {
        alignItems: 'center',
        marginBottom: 15
    },
    userImg: {
        width: 80,
        height: 80,
        borderRadius: 40
    },
    userNickname: {
        marginVertical: 5,
        fontSize: 20,
        color: '#000'
    },
    positionContainer: {
        flexDirection: 'row',
        alignItems: 'center'
    },
    positionImg: {
        width: 10,
        height: 10,
        marginRight: 2
    },
    positionText: {
        color: '#bfbfbf',
        fontSize: 10
    },
    headBottomContainer: {
        flexDirection: 'row',
        justifyContent: 'space-around'
    },
    bottomBtn: {
      alignItems: 'center'
    },
    bottomNum: {
        fontSize: 20,
        color: '#000'
    },
    bottomText: {
        color: '#bfbfbf',
        fontSize: 12
    },
    transitionView: {
        height: 5,
        backgroundColor: 'rgba(230,230,230, .5)'
    },
    settingListContainer: {
        paddingLeft: 20,
    }

});