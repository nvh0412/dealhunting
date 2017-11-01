import React, { Component } from 'react';
import { View, Text, Image, ScrollView, Animated, NativeModules, LayoutAnimation } from 'react-native';
import Colors from '../../common/Colors';
import numeral from 'numeral';
import forOwn from 'lodash/forOwn';

const { UIManager } = NativeModules;

UIManager.setLayoutAnimationEnabledExperimental && UIManager.setLayoutAnimationEnabledExperimental(true);


class BarChart extends Component {
  constructor(props) {
    super(props);

    const dataAnim = [];
    forOwn(this.props.data, (value, key) => {
      dataAnim.push({ title: key, value: 0 });
    });

    this.state = { dataAnim };
  }

  componentWillReceiveProps(nextProps) {
    LayoutAnimation.spring();

    const data = [];

    forOwn(nextProps.data, (value, key) => {
      data.push({ title: key, value: (value / 300000), price: (value / 1000000) });
    });

    this.setState({ dataAnim: data });
  }

  renderBars = () => {
    return this.state.dataAnim.map((anim) => {
      const titles = anim.title.split('-');
      const height = anim.value > 270 ? 270 : anim.value;
      return (
        <View key={anim.title}>
          <View style={[{ height: height }, styles.bar]}></View>
          <Text style={styles.value}>{(anim.price || 0).toPrecision(2)}tr</Text>
          <Text style={styles.title}>{titles[2]}/{titles[1]}</Text>
        </View>
      );
    });
  }

  render() {
    return <View style={styles.container}>{this.renderBars()}</View>
  }
}

const styles = {
  container: {
    flex: 1,
    flexDirection: 'row',
    alignItems: 'flex-end',
    justifyContent: 'center',
    paddingBottom: 16,
  },
  bar: {
    width: 32,
    marginHorizontal: 2,
    backgroundColor: Colors.orangeColor
  },
  value: {
    width: '100%',
    position: 'absolute',
    top: -12,
    fontSize: 12,
    textAlign: 'center'
  },
  title: {
    width: '100%',
    fontSize: 11,
    textAlign: 'center'
  }
}

export default BarChart;

