import React, { Component } from 'react';
import { Image, Animated, View, Text, FlatList } from 'react-native';
import { connect } from 'react-redux';
import { Actions } from 'react-native-router-flux';
import moment from 'moment';
import numeral from 'numeral';
import { fetchBill, selectBill, removeBill } from '../../actions/bill';
import ActionButton from 'react-native-action-button';
import Icon from 'react-native-vector-icons/Ionicons';
import BillItem from './BillItem';
import Filter from './Filter';
import Colors from '../../common/Colors';

const DATE_FORMAT = 'YYYY-MM-DD';

class BillScreen extends Component {
  state = {
    startDate: moment(),
    progress: new Animated.Value(0)
  }

  constructor(props) {
    super(props);

    this.renderRow = this.renderRow.bind(this);
  }

  componentWillMount() {
    const dateParam = this.state.startDate.format(DATE_FORMAT);
    this.props.fetchBill(this.props.user.spree_api_key, dateParam);
  }

  _keyExtractor = (item, index) => item.id;

  renderRow({item}) {
    const { selectBill, removeBill, user } = this.props;
    return <BillItem
      item={item}
      token={user.spree_api_key}
      selectBill={selectBill}
      removeBill={removeBill}
    />;
  }

  renderHeader = () => {
    const { dateFilter, bills } = this.props;
    const d = moment(dateFilter);

    return (
      <View>
        <View style={styles.header}>
          <Image style={{ flex: 1 }} source={require('../img/bill.jpg')} />
          <View style={styles.headerContent}>
            <Text style={styles.headerTitle}>Hoá Đơn</Text>
            <Text style={styles.headerDate}>Ngày {d.date()}/{d.month()}</Text>
            <View style={styles.billSummary}>
              <Text style={styles.billSummaryDetail}>Tổng: {numeral(bills.reduce((acc, bill) => (acc + Number.parseFloat(bill.price)), 0)).format('0,0')}</Text>
            </View>
          </View>
        </View>
      </View>
    );
  }

  render() {
    const { bills, selectBill, dateFilter, fetchBill, user } = this.props;

    return (
      <View style={styles.container}>
        <FlatList
          style={styles.list}
          data={bills}
          keyExtractor={this._keyExtractor}
          renderItem={this.renderRow}
          ListHeaderComponent={this.renderHeader}
        />
        <ActionButton buttonColor="rgba(231,76,60,1)">
          <ActionButton.Item buttonColor='#9b59b6' title="Lọc bill">
            <Filter
              date={dateFilter}
              apiKey={user.spree_api_key}
              fetchBill={fetchBill}
            />
          </ActionButton.Item>
          <ActionButton.Item buttonColor='#9b59b6' title="Tạo bill" onPress={() => { selectBill({}, 'NEW') }}>
            <Icon name="md-done-all" style={styles.actionButtonIcon} />
          </ActionButton.Item>
        </ActionButton>
      </View>
    );
  }
}

const styles = {
  container: {
    flex: 1
  },
  title: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  list: {
    borderTopWidth: 1,
    borderTopColor: Colors.grayBorder,
    backgroundColor: Colors.grayBackground
  },
  actionButtonIcon: {
    fontSize: 20,
    height: 22,
    color: 'white',
  },
  header: {
    height: 200,
    marginBottom: 8,
    borderBottomWidth: 1,
    borderBottomColor: Colors.grayBorder,
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 2},
    elevation: 2,
    shadowOpacity: 0.2
  },
  headerTitle: {
    position: 'absolute',
    fontSize: 36,
    fontWeight: 'bold',
    textAlign: 'center',
    color: 'white',
    bottom: 100,
    right: 16
  },
  headerDate: {
    position: 'absolute',
    fontSize: 24,
    fontWeight: 'bold',
    textAlign: 'center',
    color: 'white',
    bottom: 70,
    right: 16
  },
  headerContent: {
    position: 'absolute',
    backgroundColor: Colors.orangeColor,
    opacity: 0.8,
    top: 0,
    bottom: 0,
    left: 0,
    right: 0
  },
  billSummary: {
    position: 'absolute',
    bottom: 16,
    right: 16,
    borderTopWidth: 1,
    borderTopColor: 'white',
    paddingTop: 10
  },
  billSummaryDetail: {
    fontSize: 24,
    fontWeight: 'bold',
    textAlign: 'center'
  }
};

const mapStateToProps = (state) => {
  return {
    user: state.auth.user,
    bills: state.bill.bills,
    dateFilter: state.bill.dateFilter
  }
};

export default connect(mapStateToProps, { fetchBill, selectBill, removeBill })(BillScreen);

