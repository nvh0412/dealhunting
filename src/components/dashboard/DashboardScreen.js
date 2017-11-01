import React, { PureComponent } from 'react';
import { View, Text, Image, ScrollView, Animated } from 'react-native';
import Colors from '../../common/Colors';
import numeral from 'numeral';
import { connect } from 'react-redux';
import { getBillTracking, getBillReport } from '../../actions/bill';
import { getCustomerReport } from '../../actions/customer';
import Icon from 'react-native-vector-icons/Ionicons';
import BarChart from './BarChart';
import { UserProfile } from '../common';

class DashboardScreen extends PureComponent {
  componentDidMount() {
  }

  render() {
    const { user, trackingData, billReport, customerReport } = this.props;

    return (
      <ScrollView>
        <UserProfile avatar={user.avatar} name={user.name} total={trackingData.total} address="64 Âu cơ, Tân Bình, Hồ Chí Minh" />

        <View style={styles.chartContainer}>
          <BarChart data={trackingData.report} />
        </View>

        <View style={[styles.chartContainer, {height: 160}]}>
          <Image style={{ flex: 1 }} source={require('../img/product.jpg')} />
          <View style={styles.cover}>
          </View>
          <Text style={{
            backgroundColor: 'rgba(0,0,0,0)',
            position: 'absolute', bottom: 16, right: 16, color: 'white', fontSize: 56, fontWeight: 'bold', opacity: 1 }}>{billReport.total}</Text>
          <Text style={{
            backgroundColor: 'rgba(0,0,0,0)',
            position: 'absolute', bottom: 80, right: 16, color: 'white', fontSize: 32, fontWeight: 'bold' }}>Hoá đơn</Text>
        </View>

        <View style={[styles.chartContainer, {height: 160}]}>
          <Image style={{ flex: 1 }} source={require('../img/user.jpg')} />
          <View style={styles.cover}>
          </View>
          <Text style={{
            backgroundColor: 'rgba(0,0,0,0)',
            position: 'absolute', bottom: 16, right: 16, color: 'white', fontSize: 56, fontWeight: 'bold', opacity: 1 }}>{customerReport.total}</Text>
          <Text style={{
            backgroundColor: 'rgba(0,0,0,0)',
            position: 'absolute', bottom: 80, right: 16, color: 'white', fontSize: 32, fontWeight: 'bold' }}>Khách hàng</Text>
        </View>
      </ScrollView>
    );
  }
}

const styles = {
  chartContainer: {
    marginTop: 10,
    height: 320,
    backgroundColor: 'white',
    borderBottomWidth: 1,
    borderBottomColor: Colors.grayBorder,
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 2},
    elevation: 2,
    shadowOpacity: 0.2,
  },
  cover: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: Colors.orangeColor,
    opacity: 0.4
  }
};

const mapStateToProps = (state) => ({
  user: state.auth.user,
  trackingData: state.bill.trackingData,
  billReport: state.bill.billReport,
  customerReport: state.customer.customerReport
});

export default connect(mapStateToProps, { getBillTracking, getBillReport, getCustomerReport })(DashboardScreen);

