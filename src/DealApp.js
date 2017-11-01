import React, { Component } from 'react';
import { View, StatusBar } from 'react-native';
import { connect } from 'react-redux';
import Router from './Router';
import LoginScreen from './components/login/LoginScreen';
import * as firebase from 'firebase';

class DealApp extends Component {
  renderView() {
    return <Router />;
  }

  render() {
    return (
      <View style={{ flex: 1 }}>
        <StatusBar hidden/>
        {this.renderView()}
      </View>
    );
  }
}

const mapStateToProps = (state) => ({
});

export default connect(mapStateToProps)(DealApp);

