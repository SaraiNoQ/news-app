import React, {useState} from 'react';
import type {Node} from 'react';
import {
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  useColorScheme,
  View,
  Button,
  TouchableOpacity,
} from 'react-native';
import MyHeader from './components/Header';

import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import {createBottomTabNavigator} from '@react-navigation/bottom-tabs';

import {
  Colors,
  DebugInstructions,
  LearnMoreLinks,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

const Section = ({children, title, isDarkMode}): Node => {
  return (
    <View style={styles.sectionContainer}>
      <Text
        style={[
          styles.sectionTitle,
          {
            color: isDarkMode ? Colors.white : Colors.black,
          },
        ]}>
        {title}
      </Text>
      <Text
        style={[
          styles.sectionDescription,
          {
            color: isDarkMode ? Colors.light : Colors.dark,
          },
        ]}>
        {children}
      </Text>
    </View>
  );
};

const HomeScreen = ({navigation}) => {
  const [isDarkMode, setIsDarkMode] = useState(useColorScheme() === 'dark');

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  const toggleMode = () => {
    setIsDarkMode(!isDarkMode);
    navigation.navigate('Details', {isDarkMode: !isDarkMode});
  };

  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />
      <ScrollView
        contentInsetAdjustmentBehavior="automatic"
        style={backgroundStyle}>
        <MyHeader />
        <View
          style={{
            backgroundColor: isDarkMode ? Colors.black : Colors.white,
          }}>
          <TouchableOpacity style={styles.buttonContainer} onPress={toggleMode}>
            <Text
              style={[
                styles.buttonText,
                {
                  color: isDarkMode ? Colors.white : Colors.midnightblue,
                },
              ]}>
              Toggle
            </Text>
          </TouchableOpacity>
          <Section title="First" isDarkMode={isDarkMode}>
            Edit <Text style={styles.highlight}>App.js</Text> to change this
            screen and then come back to see your edits.
          </Section>
          <Section title="See Your Changes" isDarkMode={isDarkMode}>
            <ReloadInstructions />
          </Section>
          <Section title="Debug" isDarkMode={isDarkMode}>
            <DebugInstructions />
          </Section>
          <Section title="Learn More" isDarkMode={isDarkMode}>
            Read the docs to discover what to do next:
          </Section>
          <LearnMoreLinks />
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

const DetailsScreen = ({route, navigation}) => {
  const {isDarkMode} = route.params;
  return (
    <SafeAreaView style={styles.container}>
      <ScrollView
        style={{backgroundColor: isDarkMode ? Colors.black : Colors.white}}>
        <Text style={{color: isDarkMode ? Colors.white : Colors.black}}>
          Details Screen
        </Text>
        <Button
          title="Toggle"
          onPress={() => {
            // navigation.goBack();
            navigation.setOptions({title: 'Updated'});
          }}
        />
        <Button
          title="Tab"
          onPress={() => {
            navigation.navigate('Tab');
          }}
        />
      </ScrollView>
    </SafeAreaView>
  );
};

const Tab = createBottomTabNavigator();

const FeedScreen = () => {
  return (
    <View>
      <Text>Feed Screen</Text>
    </View>
  );
};
const MessageScreen = () => {
  return (
    <View>
      <Text>Message Screen</Text>
    </View>
  );
};

const TabCom = () => {
  return (
    <Tab.Navigator>
      <Tab.Screen
        name="Feed"
        component={HomeScreen}
        options={{headerShown: false}}
      />
      <Tab.Screen name="Message" component={MessageScreen} />
    </Tab.Navigator>
  );
};

const App: () => Node = () => {
  const Stack = createNativeStackNavigator();

  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen
          name="Home"
          component={TabCom}
          options={{headerShown: false}}
        />
        <Stack.Screen
          name="Details"
          component={DetailsScreen}
          options={{
            headerStyle: {
              backgroundColor: '#d81e06',
            },
            headerTintColor: '#fff',
            headerTitleStyle: {
              fontWeight: 'bold',
            },
          }}
        />
        <Stack.Screen
          name="Tab"
          component={TabCom}
          options={{headerShown: false}}
          navigationOptions={{
            headerShown: false,
          }}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
  },
  highlight: {
    fontWeight: '700',
  },
  buttonContainer: {
    marginTop: 32,
    marginBottom: 32,
    borderRadius: 5,
    backgroundColor: 'dodgerblue',
    border: '1px solid black',
    height: 40,
    lineHeight: 32,
    width: 120,
    marginLeft: 'auto',
    marginRight: 'auto',
  },
  buttonText: {
    fontSize: 16,
    lineHeight: 40,
    fontWeight: '400',
    textAlign: 'center',
  },
});

export default App;
