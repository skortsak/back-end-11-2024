import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Address, Person } from '../models/Person';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {

  constructor(private http: HttpClient) {}

  submitSignup(form: NgForm) {
    const fVal = form.value;
    const person = new Person(0, fVal.email, fVal.password, fVal.firstName, fVal.lastName, 
      new Address(fVal.street, fVal.houseNo, fVal.apatmentNo, fVal.postalIndex, fVal.city, 
        fVal.county, fVal.country), false
    );
    this.http.post<any[]>("http://localhost:8080/signup", person).subscribe();
  }

}
