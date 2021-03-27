'use strict';


const React = require('react'); // <1>
const ReactDOM = require('react-dom'); // <2>
const client = require('./client'); // <3>

class App extends React.Component { // <1>

	constructor(props) {
		super(props);
		this.state = {persons: []};
	}

	componentDidMount() { // <2>
		client({method: 'GET', path: '/api/persons'}).done(response => {
			this.setState({persons: response.entity._embedded.persons});
		});
	}

	render() { 
		return (
			<PersonList persons={this.state.persons}/>
		)
	}
}


class PersonList extends React.Component{
	render() {
		const persons = this.props.persons.map(person =>
			<Person key={person._links.self.href} person={person}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Comments</th>
					</tr>
					{persons}
				</tbody>
			</table>
		)
	}
}
class Person extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.person.firstName}</td>
				<td>{this.props.person.lastName}</td>
				<td>{this.props.person.comments}</td>
			</tr>
		)
	}
}
ReactDOM.render(
	<App />,
	document.getElementById('react')
)
