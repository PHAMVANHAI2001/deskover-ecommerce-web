import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {RestApiService} from "@services/rest-api.service";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {DatatablesResponse} from "@/entites/datatables-response";
import {Order, OrderStatus} from "@/entites/order";

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  orderUrl = environment.globalUrl.orderApi;
  ghtkUrl = environment.globalUrl.ghtkApi;

  constructor(private restApi: RestApiService, private httpClient: HttpClient) { }

  getOrdersForDatatables(tableQuery: any, params: HttpParams): Observable<DatatablesResponse> {
    return this.restApi.post(this.orderUrl + '/datatables', tableQuery, params);
  }

  getOrderStatuses(): Observable<OrderStatus[]> {
    return this.restApi.get(this.orderUrl + '/statuses');
  }

  getOPayments(): Observable<OrderStatus[]> {
    return this.restApi.get(this.orderUrl + '/payments');
  }

  getShippingUnits(): Observable<OrderStatus[]> {
    return this.restApi.get(this.orderUrl + '/shipping-units');
  }

  getOrder(id: number): Observable<Order> {
    return this.restApi.get(this.orderUrl + '/' + id);
  }

  countByStatus(status: string): Observable<number> {
    return this.restApi.get(this.orderUrl + '/count-by-status/' + status);
  }

  changeOrderStatus(orderCode: string): Observable<Order> {
    return this.restApi.post(this.orderUrl + '/change-status-code/' + orderCode);
  }

  cancelOrder(orderCode: string): Observable<any> {
    return this.restApi.put(this.orderUrl + '/cancel/' + orderCode);
  }

  refundOrder(orderCode: string): Observable<any> {
    return this.restApi.put(this.orderUrl + '/refund/' + orderCode);
  }

  confirmOrder(order: Order): Observable<any> {
    return this.restApi.post(this.ghtkUrl + '/shipment/order', order, null,{
      headers: {
        'Token': environment.ghtkToken
      }
    });
  }
}
