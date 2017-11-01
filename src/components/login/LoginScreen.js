import React, { Component } from 'react';
import {
  View,
  Text,
  Image,
  Input,
  ImageBackground,
  Animated,
  KeyboardAvoidingView,
  TouchableWithoutFeedback
} from 'react-native';
import { Actions } from 'react-native-router-flux';
import LoginForm from './LoginForm';

class LoginScreen extends Component {
  state = {
    showForm: false,
    scaleAnim: new Animated.Value(0.3),
  }

  constructor(props) {
    super(props);

    this.openEmailLoginHandler = this.openEmailLoginHandler.bind(this);
    this.backBtnHandler = this.backBtnHandler.bind(this);
  }

  openEmailLoginHandler = () => {
    Animated.spring(
      this.state.scaleAnim,
      {
        toValue: 1,
        friction: 6
      }
    ).start();
    this.setState({ showForm: true });
  }

  backBtnHandler() {
    Animated.spring(
      this.state.scaleAnim,
      {
        toValue: 0.25,
        friction: 6
      }
    ).start();
    this.setState({ showForm: false });
  }

  loginWithFbHandler = () => {
  }

  render() {
    let { scaleAnim } = this.state;

    return (
      <ImageBackground
        style={styles.container}
        source={require('./img/login_background.jpg')}
      >
        <Text style={styles.header}>Quyen Beauty</Text>
        <Text style={styles.subHeader}>Admin Dashboard</Text>
        <Animated.View style={{ ...styles.content, flex: scaleAnim }}
        >
          <View style={{ display: !this.state.showForm ? 'flex' : 'none' }}>
            <Text style={styles.title}>Quản lý dễ hơn, kiếm tiền dễ hơn</Text>
            <Text style={styles.description}>Đăng nhập để quản lý danh sách hoá đơn và xem thông tin khách hàng từ QuyenBeauty</Text>
            <TouchableWithoutFeedback onPress={this.openEmailLoginHandler}>
              <View style={styles.loginCta}>
                <Image style={styles.emailIcon} source={require('./img/mail_icon.png')} />
                <View style={styles.loginCtaContainer}>
                  <Text style={styles.loginCtaText}>Nhập tài khoản để tiếp tục</Text>
                </View>
              </View>
            </TouchableWithoutFeedback>
            <TouchableWithoutFeedback onPress={this.loginWithFbHandler}>
              <View style={styles.loginCta}>
                <Image style={styles.emailIcon} source={require('./img/facebook_icon.png')} />
                <View style={styles.loginCtaContainer}>
                  <Text style={styles.loginCtaFBText}>Đăng nhập bằng Facebook</Text>
                </View>
              </View>
            </TouchableWithoutFeedback>
          </View>
          <KeyboardAvoidingView behavior="padding" style={{ display: this.state.showForm ? 'flex' : 'none', flex: 1 }}>
            <TouchableWithoutFeedback onPress={this.backBtnHandler}>
              <Image style={styles.emailIcon} source={require('./img/back_icon.png')} />
            </TouchableWithoutFeedback>
            <LoginForm />
          </KeyboardAvoidingView>
        </Animated.View>
      </ImageBackground>
    );
  }
}

const styles = {
  container: {
    flex: 1
  },
  content: {
    marginTop: 'auto',
    padding: 10,
    paddingTop: 15,
    flex: 0.25,
    backgroundColor: 'white'
  },
  header: {
    position: 'absolute',
    fontSize: 40,
    fontWeight: 'bold',
    backgroundColor: 'transparent',
    color: 'white',
    width: 150,
    left: 10,
    top: 20
  },
  subHeader: {
    position: 'absolute',
    fontSize: 32,
    backgroundColor: 'transparent',
    fontWeight: 'bold',
    color: '#E9802C',
    left: 10,
    top: 120
  },
  title: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  description: {
    marginTop: 10
  },
  loginCta: {
    marginTop: 10,
    flexDirection: 'row',
  },
  loginCtaText: {
    color: '#E9802C'
  },
  loginCtaFBText: {
    color: '#3b5998'
  },
  loginCtaContainer: {
    flex: 1,
    marginLeft: 10,
    borderBottomWidth: 1,
    borderColor: '#dfe1df',
    paddingTop: 3,
    paddingBottom: 10
  },
  emailIcon: {
    width: 24,
    height: 24,
  }
}

export default LoginScreen;

