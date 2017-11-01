import React, { Component } from 'react';
import {
  View,
  Text,
  Image,
  TextInput,
  Animated,
  TouchableOpacity
} from 'react-native';
import { connect } from 'react-redux';
import { Actions } from 'react-native-router-flux';
import { emailLoginChange, passwordLoginChange, logIn } from '../../actions/auth';
import Colors from '../../common/Colors';

class LoginForm extends Component {
  render() {
    const {
      email,
      password,
      error,
      emailLoginChange,
      passwordLoginChange,
      logIn,
      loggedIn
    } = this.props;

    if (loggedIn) { Actions.main(); }

    return (
      <View style={styles.loginForm}>
        <View style={styles.content}>
          <Text style={styles.title}>Tài khoản của bạn?</Text>
          <Text style={styles.description}>Địa chỉ email giúp bạn bảo mật tài khoản, phục hồi mật khẩu và nhận các thông tin dành riêng cho bạn</Text>
          <View style={styles.inputContainer}>
            <Image source={require('./img/mail_icon.png')} style={styles.icon} />
            <TextInput
              keyboardType="email-address"
              value={email}
              autoCorrect={false}
              underlineColorAndroid='rgba(0,0,0,0)'
              onChangeText={(text) => emailLoginChange(text)}
              style={styles.input}
            />
          </View>
          <View style={styles.inputContainer}>
            <Image source={require('./img/password_icon.png')} style={styles.icon} />
            <TextInput
              secureTextEntry
              value={password}
              underlineColorAndroid='rgba(0,0,0,0)'
              onChangeText={(text) => passwordLoginChange(text)}
              style={styles.input}
            />
          </View>
          <Text style={styles.error}>{error}</Text>
        </View>
        <View style={styles.ctaContainer}>
          <TouchableOpacity onPress={() => logIn({email: (email || '').toLowerCase(), password})}>
            <View style={styles.ctaButton}>
              <Image source={require('./img/next_icon.png')} style={styles.icon} />
            </View>
          </TouchableOpacity>
        </View>
      </View>
    );
  }
}

const styles = {
  loginForm: {
    paddingTop: 15,
    flex: 1
  },
  content: {
    flex: 1
  },
  title: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  description: {
    marginTop: 10,
    marginBottom: 35
  },
  inputContainer: {
    marginTop: 20,
    flexDirection: 'row',
    alignItems: 'center'
  },
  icon: {
    height: 24,
    width: 24
  },
  input: {
    color: '#000',
    height: 40,
    fontSize: 16,
    borderColor: Colors.grayBorder,
    borderBottomWidth: 1,
    flex: 1,
    marginLeft: 10
  },
  ctaContainer: {
    alignItems: 'flex-end'
  },
  ctaButton: {
    backgroundColor: Colors.grayButton,
    width: 50,
    height: 50,
    borderRadius: 25,
    alignItems: 'center',
    justifyContent: 'center',
    marginBottom: 10
  },
  error: {
    marginTop: 15,
    color: 'red',
    textAlign: 'center'
  }
}

const mapStateToProps = (state) => ({
  ...state.auth
});

export default connect(mapStateToProps, { emailLoginChange, passwordLoginChange, logIn })(LoginForm);

