import { InternationalRouteService } from './../../service/international-route.service';
import { MenuItem, MessageService } from 'primeng/api';
import { Routes } from '../../../../model/ShipmentRoutes';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-international-road-routes',
  templateUrl: './add-international-road-routes.component.html',
  styleUrls: ['./add-international-road-routes.component.scss'],
  providers: [ MessageService ]
})
export class AddInternationalRoadRoutesComponent {

  items: MenuItem[] | undefined;
  routes: Routes = {
    id: null,
    destination: null,
    driver: null,
    eta: null,
    etd: null,
    origin: null,
    route: null,
  }

  routeNumbers: any;


  constructor(private internationalRouteService: InternationalRouteService,
    private messageService: MessageService,
    private router: Router) { }


  ngOnInit(): void {
    this.items = [{ label: 'International Route List For Road', routerLink: '/international-routes-for-road' }, { label: 'Add Route' }];
  }

  onSubmit() {
    this.internationalRouteService
      .addInternationalRoute(this.routes).subscribe(res => {
        this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Location is added' });
        setTimeout(() => {
          this.router.navigate(['/domestic-route']);
        }, 800);
      }, error => {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Route is not added' });
      })
  }

  getDomesticRoute() {
    this.routeNumbers = []

    if (this.routes.origin !== null && this.routes.destination !== null) {
      this.internationalRouteService
        .getInternationalRoute(this.routes.origin!, this.routes.destination!).subscribe((res: any) => {
          this.routes = res;

        }, (error: any) => {
          console.log(error);
          this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
        })

    } else {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'You must have to select origin and destination port' });
    }
  }
}

