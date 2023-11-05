export class  PermissionRoutes{
   
    PermissionRoutes:{permission:string,route:string[]}[]=[
        {permission:'Dash Board',route:['/home']},
        {permission:'Domestic Shipment',route:['/domestic-shipping','/^domestic-shipping-history\/\d+$/','/add-domestic-shipping','/^update-domestic-shipping\/\d+$/']},
        {permission:'International Shipment',route:['/international-tile','/international-shipment-by-road']},
        {permission:'International Shipment By Road',route:['/international-shipment-by-road','/^international-shipment-history-by-road\/\d+$/','add-international-shipment-by-road','/^update-international-shipment-by-road\/\d+$/']},
        {permission:'International Shipment By Air',route:['/international-shipment-by-air','/^international-shipment-history-by-air\/\d+$/','add-international-shipment-by-air','/^update-international-shipment-by-air\/\d+$/']},
        {permission:'Domestic Summary',route:['/domestic-summary']},
        {permission:'International Summary By Air',route:['/international-summary-by-air']},
        {permission:'International Summary By Road',route:['/international-summary-by-road']},
        {permission:'User',route:['/user','/add-user','/^edit-user\/\d+$/']},
        {permission:'Driver',route:['/driver','/add-driver','/^edit-driver\/\d+$/']},
        {permission:'Location',route:['/location','/add-location','/^edit-location\/\d+$/']},
        {permission:'Location Port',route:['/location-port','/add-location-port','/^edit-location-port\/\d+$/']},
        {permission:'Shipment Status',route:[]},
        {permission:'Vehicle Type',route:['/vehicle-type','/add-vehicle-type','/^edit-vehicle-type\/\d+$/']},
        {permission:'Role',route:['/role','/^edit-role\/\d+$/']}
    ]

    getPermissionForUrl(url: string): string | null {
        for (const route of this.PermissionRoutes) {
          for (const pattern of route.route) {
            const regexPattern = pattern.startsWith('/') ? new RegExp(pattern.slice(1)) : new RegExp(`^${pattern}$`);
            if (regexPattern.test(url)) {
              return route.permission;
            }
          }
        }
        return null; 
    }
}
