import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { DomesticShipment } from 'src/app/model/DomesticShipment';
import { DatePipe } from '@angular/common';
import { DomesticShippingService } from 'src/app/page/shipping-order/domestic/service/domestic-shipping.service';
import { ClipboardService } from 'ngx-clipboard';
import { SummaryService } from '../../service/summary.service';

@Component({
  selector: 'app-view-domestic-shipping-for-summary',
  templateUrl: './view-domestic-shipping-for-summary.component.html',
  styleUrls: ['./view-domestic-shipping-for-summary.component.scss'],
  providers:[MessageService]
})
export class ViewDomesticShippingForSummaryComponent {
  items: MenuItem[] | undefined;
  resultArray:{overagesAWBs:string|undefined,shortagesAWBs:string|undefined,securityTag:string|undefined,damageAWBs:string|undefined}[]=[]
  copyOveragesAWBs!:string
  copyShortagesAWBs!:string
  copySecurityTag!:string
  copyDamageAWBs! : string;

  domesticShipment:DomesticShipment={
    originFacility: null,
    originLocation: null,
    refrigeratedTruck: false,
    destinationFacility: null,
    destinationLocation: null,
    routeNumber: null,
    numberOfShipments: null,
    weight: null,
    // etd: null,
    // eta: null,
    atd: null,
    driverName: null,
    driverContact: null,
    referenceNumber: null,
    vehicleType: null,
    numberOfPallets: null,
    numberOfBags: null,
    vehicleNumber: null,
    tagNumber: null,
    // sealNumber: null,
    status: null,
    remarks: null,
    ata: null,
    totalShipments: null,
    overages: null,
    overagesAwbs: null,
    received: null,
    shortages: null,
    shortagesAwbs: null,
    attachments: null,
    preAlertNumber: undefined,
    transitTimeTaken: null,
    preAlertType: null,
    originCountry: undefined,
    destinationCountry: undefined,
    numberOfBoxes: undefined,
    routeNumberId: null,
    damage: null,
    damageAwbs: null,
    numberOfPalletsReceived: null,
    numberOfBagsReceived: null
  };

  domesticShipmentId!:number;
  domesticShipmentHistory:any;
  emailAttribute!: string;

  constructor(
    private _clipboardService: ClipboardService,
    private domesticShipmentService:DomesticShippingService,
    private router:Router,
    private messageService: MessageService,
    private route:ActivatedRoute,
    private summaryService:SummaryService) { }
  


  
  ngOnInit(): void {
    this.domesticShipmentId = +this.route.snapshot.paramMap.get('id')!;
    this.domesticShipmentById(this.domesticShipmentId);
    this.getDomesticShipmentHistoryByDomesticShipmentId(this.domesticShipmentId);
    this.items = [{ label: 'Domestic Inbound',routerLink:'/domestic-summary'},{ label: 'View Domestic Inbound'}];
    this.getDomesticEmail(this.domesticShipmentId);
  }

  getDomesticShipmentHistoryByDomesticShipmentId(id:number){
    this.domesticShipmentService.getDomesticShipmentHistoryByDomesticShipmentId(id).subscribe((res:any)=>{
      this.domesticShipmentHistory=res;
    },(error:any)=>{
      if(error.error.body){
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      }else{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }   
    })
  }

  

   domesticShipmentById(id:number){
    this.domesticShipmentService.getDomesticShipmentById(id).subscribe((res:DomesticShipment)=>{

      this.domesticShipment=res;
      this.makeModelForTable();
    },(error:any)=>{
      if(error.error.body){
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      }else{
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }         
    })
   }
   makeModelForTable() {
    let overagesAWBsArray:any;
    let shortagesAWBsArray:any;
    let securityTagArray:any;
    let DamageAWBsArray:any;
    if(this.domesticShipment.overagesAwbs!=null){
     overagesAWBsArray = this.domesticShipment.overagesAwbs!.split(',');
     this.copyOveragesAWBs=overagesAWBsArray.join('\n');
    }else{
       overagesAWBsArray =[]
    }
    if(this.domesticShipment.shortagesAwbs!=null){
     shortagesAWBsArray = this.domesticShipment.shortagesAwbs!.split(',');
     this.copyShortagesAWBs=shortagesAWBsArray.join('\n');
    }else{
       shortagesAWBsArray =[]
    }
    if(typeof this.domesticShipment.tagNumber == "string"){
     securityTagArray = this.domesticShipment.tagNumber!.split(',');
     this.copySecurityTag=securityTagArray.join('\n')
    }else{
       securityTagArray = []
    }
    if(this.domesticShipment.damageAwbs!=null){
      DamageAWBsArray = this.domesticShipment.damageAwbs!.split(',');
      this.copyDamageAWBs=DamageAWBsArray.join('\n');
     }else{
      DamageAWBsArray =[]
     }
   

    // Determine the maximum length among the three arrays
    const maxLength = Math.max(
      overagesAWBsArray.length,
      shortagesAWBsArray.length,
      securityTagArray!.length,
      DamageAWBsArray!.length
    );

    // Create an array to store objects
   

    // Loop through the arrays to create objects
    for (let i = 0; i < maxLength; i++) {
      const obj: any = {};
      if (i < overagesAWBsArray.length) {
        obj.overagesAWBs = overagesAWBsArray[i];
      }
      if (i < shortagesAWBsArray.length) {
        obj.shortagesAWBs = shortagesAWBsArray[i];
      }
      if (i < securityTagArray!.length) {
        obj.securityTag = securityTagArray![i];
      }
      if (i < DamageAWBsArray!.length) {
        obj.damageAWBs = DamageAWBsArray![i];
      }
      this.resultArray.push(obj);
    }
  }

  AnimationOveragesAWBs:boolean=false;
  AnimationSecurityTag:boolean=false;
  AnimationShortagesAWBs:boolean=false;
  AnimationDamageAWBs:boolean=false;

  onCopiedAnimationOveragesAWBs(){
  this.AnimationOveragesAWBs=true;
  this._clipboardService.copy(this.copyOveragesAWBs)
  setTimeout(() => {
  this.AnimationOveragesAWBs=false;
   }, 2000);
  }
  onCopiedAnimationSecurityTags(){
    this.AnimationSecurityTag=true;
    this._clipboardService.copy(this.copySecurityTag)
  setTimeout(() => {
    this.AnimationSecurityTag=false;
  }, 2000);
    }
    onCopiedAnimationShortagesAWBs(){
      this.AnimationShortagesAWBs=true;
      this._clipboardService.copy(this.copyShortagesAWBs)
    setTimeout(() => {
      this.AnimationShortagesAWBs=false;
    }, 2000);
    }


    onCopiedAnimationDamageAWBs(){
      this.AnimationDamageAWBs=true;
      this._clipboardService.copy(this.copyDamageAWBs)
    setTimeout(() => {
      this.AnimationDamageAWBs=false;
    }, 2000);
    }

    getDomesticEmail(id:number){
      this.summaryService.getDomesticEmail(id).subscribe((res)=>{
        this.emailAttribute='mailto:'.concat(res.to,'?cc=',res.cc)
      },(error)=>{
        console.log(error);
      })
    }
  
}
