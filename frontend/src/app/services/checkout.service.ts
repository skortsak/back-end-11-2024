import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page, PageRequest } from '../models/page';
import { Checkout } from '../models/checkout';
import { environment } from 'src/environments/environment';
import { RestUtil } from './rest-util';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {
  private readonly baseUrl = environment.backendUrl + '/api/checkout';

  constructor(private http: HttpClient) {}

  getCheckouts(filter: Partial<PageRequest>): Observable<Page<Checkout>> {
    const url = `${this.baseUrl}/getCheckouts`;
    const params = RestUtil.buildParamsFromPageRequest(filter);
    return this.http.get<Page<Checkout>>(url, { params });
  }
}
