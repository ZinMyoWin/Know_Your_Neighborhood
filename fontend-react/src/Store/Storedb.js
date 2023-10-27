import React, { Component } from 'react';
import axios from 'axios';
import '../Store/Store.css';

export class Storedb extends Component {

    constructor(props) {
        super(props);
        this.state = {
            stores: [],
            searchField: ''
        };
    }

    componentDidMount() {
        axios.get('http://localhost:8080/kyn/viewStores')
            .then((response) => {
                this.setState({
                    stores: response.data
                });
            })
            .catch(error => {
                console.error('There was an error!', error);
            });
    }

    onSearchChange = event => {
        this.setState({ searchField: event.target.value }, () => {
            axios.get(`http://localhost:8080/kyn/search/${this.state.searchField}`)
                .then((response) => {
                    this.setState({
                        stores: response.data
                    });
                })
                .catch(error => {
                    console.error('There was an error!', error);
                });
        });
    };

    render() {
        const { stores } = this.state;
        return (
            <>
            <h1 className='title'>Store Lists</h1>
            <div className='search-input'>

            <input type='search' placeholder='search stores' onChange={this.onSearchChange} />
            </div>
            <div className='store-container'>
                {stores.map(store =>
                    <div className='store-card' key={store.storeId}>
                        <h3>Store Name: <span>{store.storeName}</span></h3>
                        <p>Store Type: {store.storeType}</p>
                        <p>Location: {store.location}</p>
                    </div>)}
            </div>
            </>
        );
    }
}

export default Storedb;