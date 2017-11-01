import React, { Component } from 'react';
import { View, StatusBar } from 'react-native';
import { Provider } from 'react-redux';
import configureStore from './src/store/configureStore';
import DealApp from './src/DealApp';

export default class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: true,
      store: configureStore(() => this.setState({ isLoading: false }))
    };
  }

  render() {
    if (this.state.isLoading) {
      return null;
    }

    return (
      <Provider store={this.state.store}>
        <DealApp />
      </Provider>
    );
  }
}

