import { InternationalShipment } from './../../../../model/InternationalShipment';
import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { Routes } from '../../../../model/ShipmentRoutes';
import { InternationalRouteService } from '../../service/international-route.service';
import { Router } from '@angular/router';
import { DomesticShipment } from '../../../../model/DomesticShipment';
// import { LocationPort } from '../../../../model/LocationPort';
import { Location } from '../../../../model/Location';
import { LocationService } from '../../../location/service/location.service';
import { DatePipe } from '@angular/common';
import { InternationalRoutes } from 'src/app/model/InternationalRoute';
import { LocationPortService } from 'src/app/page/location-port/service/location-port.service';

@Component({
  selector: 'app-add-international-air-routes',
  templateUrl: './add-international-air-routes.component.html',
  styleUrls: ['./add-international-air-routes.component.scss'],
  providers: [MessageService, DatePipe]
})
export class AddInternationalAirRoutesComponent {

  items: MenuItem[] | undefined;
  type!:Type[];
  internationalRoute: InternationalRoutes = {
    id: null,
    destination: null,
    driverId: null,
    flight: null,
    type: 'Air',
    eta: null,
    etd: null,
    origin: null,
    route: null,
    remarks: undefined
  }

  // location!: LocationPort[];

  routeNumbers: any;
  minETDDate: Date = new Date();
  // destination!: LocationPort[];

  constructor(
    private internationalRouteService: InternationalRouteService,
    private locationPortService: LocationPortService,
    private messageService: MessageService,
    private datePipe: DatePipe,
    private router: Router) { }


  ngOnInit(): void {
    this.items = [{ label: 'International Route List For Air', routerLink: '/international-routes-for-air' }, { label: 'Add International Route For Air' }];
    this.getInternationalLocations();
    this.type=[{
      type:'Air'
    },{
      type:'Road'
    }]
  }

  getInternationalLocations() {
    // this.locationPortService.getAllLocationPort().subscribe((res: LocationPort[]) => {
    //   this.location = res.filter(el => el.status);
    // }, error => {
    //   if (error.error.body) {
    //     this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    //   } else {
    //     this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
    //   }
    // })
  }

  onSubmit() {
    this.internationalRoute.etd = this.datePipe.transform(this.internationalRoute.etd, 'HH:mm:ss')
    this.internationalRoute.eta = this.datePipe.transform(this.internationalRoute.eta, 'HH:mm:ss')
    this.addInternationalRoutes(this.internationalRoute);
  }

  addInternationalRoutes(internationalRoute: InternationalRoutes) {
    
    this.internationalRouteService.addInternationalRoute(internationalRoute).subscribe((res: InternationalRoutes) => {
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'International Route For Air Added Successfully' });
      setTimeout(() => {
        this.router.navigate(['/international-routes-for-air']);
      }, 800);
    }, (error: any) => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }

  onETDDateSelected(selectedETDDate: Date) {
    const minETDDate = new Date(selectedETDDate);

    minETDDate.setDate(minETDDate.getDate() + 1);

    this.minETDDate = minETDDate;
  }

}

interface Type{
  type:string;
}
