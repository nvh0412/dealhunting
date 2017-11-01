import React, { Component } from 'react';
import { View, Text, TouchableOpacity } from 'react-native';
import Colors from '../common/Colors';
import { connect } from 'react-redux';
import { logOut } from '../../actions/auth';

class SettingScreen extends Component {
  render() {
    return (
      <View style={styles.container}>
        <TouchableOpacity onPress={this.props.logOut}>
          <Text style={styles.logout}>Đăng xuất</Text>
        </TouchableOpacity>
        <Text style={styles.version}>MADE WITH HOA.N</Text>
      </View>
    );
  }
}

const styles = {
  container: {
    flex: 1,
    backgroundColor: Colors.grayBackground,
    alignItems: 'center'
  },
  version: {
    color: 'white',
    marginTop: 10,
    fontSize: 14,
    fontWeight: 'bold'
  },
  logout: {
    marginTop: 16,
    width: 280,
    padding: 10,
    textAlign: 'center',
    backgroundColor: Colors.orangeColor,
    color: 'white'
  }
}

export default connect(null, { logOut })(SettingScreen);

