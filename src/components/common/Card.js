import React, { Component } from 'react';
import { View } from 'react-native';

export default class Card extends Component {
  render() {
    return (
      <View style={styles.containerStyle}>
        {this.props.children}
      </View>
    );
  }
};

const styles = {
  containerStyle: {
    borderWidth: 1,
    borderRadius: 2,
    borderBottomWidth: 0,
    backgroundColor: 'white',
    borderColor: '#ddd',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 2,
    elevation: 1,
    marginLeft: 5,
    marginRight: 5,
    marginTop: 10
  }
};

