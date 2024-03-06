<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>email</title>
    <style>
        #customers {
  font-family: Arial, Helvetica, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

#customers td, #customers th {
  border: 1px solid #ddd;
  padding: 8px;
}

#customers tr:nth-child(even){background-color: #f2f2f2;}
    </style>
</head>
<body>
    <table id="customers" style="width: 100%;">
        <tbody>
            <tr>
                <td style="background-color:#943634;color:white;text-align: center;">
                        Received on ${field1}
                </td>
                <td style="background-color:#943634;color:white; text-align: center;">
                        Pre- alert
                </td>
                <td style="background-color:#943634;color:white; text-align: center;">
                        Received
                </td>
            </tr>
            <tr>
                <td style="font-weight: bolder;">
                    Total bags
                </td>
                <td style="text-align: center;">
                    ${field2}
                </td>
                <td style="text-align: center;">
                    ${field3}
                </td>
            </tr>
            <tr>
                <td style="font-weight: bolder;">
                    Total shipments
                </td>
                <td style="text-align: center;" >
                    ${field4}
                </td>
                <td style="text-align: center;">
                    ${field5}
                </td>
            </tr>
            <tr>
                <td style="font-weight: bolder;">
                    Total pallets
                </td>
                <td style="text-align: center;" >
                    ${field6}
                </td>
                <td style="text-align: center;" colspan="3">
                    ${field7}
                </td>
            </tr>


            <tr>
                <td></td>
                <td style="background-color:#943634;color:white; text-align: center;">
                        PCS
                </td>
                <td style="background-color:#943634;color:white; text-align: center;">
                        AWB NUMBER
                </td>
            </tr>
            <tr>
                <td style="font-weight: bolder;">
                    Shortages
                </td>
                <td style="text-align: center;">
                    ${field8}
                </td>
                <td style="text-align: center;" >
                    ${field9}
                </td>
            </tr>
            <tr>
                <td style="font-weight: bolder;">
                    Overages
                </td>
                <td style="text-align: center;" >
                    ${field10}
                </td>
                <td style="text-align: center;">
                    ${field11}
                </td>
            </tr>
            <tr>
                <td style="font-weight: bolder;">
                    Damage
                </td>
                <td style="text-align: center;" >
                    ${field12}
                </td>
                <td style="text-align: center;" colspan="3">
                    ${field13}
                </td>
            </tr>

        </tbody>
    </table>
</body>
</html>