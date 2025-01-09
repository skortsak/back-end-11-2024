import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Person } from '../models/Person';

type Token = {token: string, expiration: Date, admin: boolean};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private backendUrl = "http://localhost:8080"; // subject --> observable 
  loggedInStatus = new BehaviorSubject<{loggedIn: boolean, admin: boolean}>({loggedIn: false, admin: false});
  // new Subject() --> ilma algväärtuseta
  // new BehaviorSubject() --> algväärtusega
  // new RepeatSubject() --> salvestab ka kõik vahepealseid seisundeid
  // new AsyncSubject() --> API päringutega seotud subjectid (mis vajavad algväärtust endpoindist)

  constructor(private http: HttpClient) { }

  determineIfLogedIn() {
    return this.http.get<Person>(this.backendUrl + "/profile",
      {headers: {"Authorization": "Bearer " + sessionStorage.getItem("token")}}
    ).pipe(
      tap(response => { 
        if (response.id && response.email) {
          this.loggedInStatus.next({loggedIn: true, admin: response.admin});
        } else {
          this.loggedInStatus.next({loggedIn: false, admin: false});
        }
      })
    );
  }

  login(value: {email: string, password: string}): Observable<Token> {
    return this.http.post<Token>(this.backendUrl + "/login", value).pipe(
      tap(response => { 
        if (response.token) {
          this.loggedInStatus.next({loggedIn: true, admin: response.admin});
        } else {
          this.loggedInStatus.next({loggedIn: false, admin: false});
        }
      })
    );
  }
}

// .pipe() --> saan kasutada API endpointi tagastust
// .tap() mis on pipe() sees võimaldab võtta tagastuse ja midagi sellega teha
// .map() mis on pipe() sees võimaldab tagastust kõikidele kes selle külge .subscribe() teevad
