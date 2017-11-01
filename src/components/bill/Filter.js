import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  TextInput
} from 'react-native';
import DatePicker from 'react-native-datepicker';
import moment from 'moment';
import Colors from '../../common/Colors';

const DATE_FORMAT = 'YYYY-MM-DD';

class Filter extends React.PureComponent {
  constructor(props) {
    super(props);
  }

  onDateChangeHandler = (date) => {
    const { apiKey, fetchBill } = this.props;
    const dateParam = moment(date).format(DATE_FORMAT);
    fetchBill(apiKey, dateParam);
  };

  render() {
    return (
      <DatePicker
        style={styles.datePicker}
        date={this.props.date}
        hideText
        placeholder="Lọc bill theo ngày"
        confirmBtnText="Chọn"
        cancelBtnText="Huỷ"
        customStyles={styles.customStyle}
        onDateChange={this.onDateChangeHandler}
      />
    );
  }
}

const styles = {
  customStyle: {
  }
};

export default Filter;

